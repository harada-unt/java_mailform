package com.unt.mailform.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.unt.mailform.model.Contact;
import com.unt.mailform.model.ContactDto;
import com.unt.mailform.service.ContactService;
import com.unt.mailform.service.MailSenderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
public class ContactFormController {

    // サービスクラスをDIする
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ContactService contactService;
    
    // フォーム確認画面
    @GetMapping("/confirm")
    public String showForm(Model model) {
        model.addAttribute("contactDto", new ContactDto());
        return "index";
    }
    @PostMapping("confirm")
    public String submitForm(
        @Valid @ModelAttribute ContactDto contactDto,
        BindingResult bindingResult,
        Model model
    ) {
        if ( bindingResult.hasErrors()) {
            return "index";
        }
        return "confirm";
    }

    // メール送信とデータベース保存
    @GetMapping("/complete")
    public String showCompleteForm(Model model) {
        model.addAttribute("form", new ContactDto());
        return "index";
    }
    @PostMapping("/complete")
    public String sendFormMail(
        @Valid @ModelAttribute ContactDto contactDto,
        BindingResult bindingResult,
        Model model
    ) {
        // メールを送信できなかった時の例外処理
        try {
            mailSenderService.sendMail(
                contactDto.getName(),
                contactDto.getEmail(),
                contactDto.getSubject(),
                contactDto.getContent()
            );
        } catch (Exception e) {
            model.addAttribute("message", "メールの送信に失敗しました。時間をおいて再度お試しください。");
            return "error";
        }

        contactService.saveContact(contactDto);
        return "complete";
    }

    // お問い合わせ一覧表示
    @GetMapping("/list")
    public String showList(Model model, Pageable pageable) {
        Page<Contact> pageList = contactService.getContact(pageable);
        List<Contact> contactList = pageList.getContent();
        model.addAttribute("pages", pageList);
        model.addAttribute("contacts", contactList);
        return "list";
    }
    // お問い合わせ検索機能
    @PostMapping("/list")
    public String searchList(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email,
    Model model, Pageable pageable) {
        String keyword = null;
        if (name != null && !name.isBlank()) {
            keyword =name;
        } else if (email != null && !email.isBlank()) {
            keyword = email;
        }

        Page<Contact> pageList = contactService.getContact(pageable);

        List<Contact> contacts = pageList.getContent();

        if (keyword == null) {
            // キーワードが未指定の場合は全件取得する
            contacts = contactService.getContact(pageable).getContent();
        } else {
            pageList = contactService.searchContact(keyword, pageable);
            contacts = pageList.getContent();
        }

        if (contacts.isEmpty()) {
            model.addAttribute("message", "該当するお問い合わせは見つかりませんでした。");
        } 

        model.addAttribute("contacts", contacts);
        model.addAttribute("pages", pageList);
        return "list";
    }

    // お問い合わせ編集機能
    @GetMapping("/list/update/{id}")
    String updateContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        ContactDto contactDto =new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setName(contact.getName());
        contactDto.setEmail(contact.getEmail());
        contactDto.setSubject(contact.getSubject());
        contactDto.setContent(contact.getContent());
        model.addAttribute("contactDto", contactDto);
        return "update";
    }
    @PostMapping("/list/update/{id}")
    String updateForm(@PathVariable Long id, @Valid ContactDto contactDto,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        return contactService.updateContact(id, contactDto) != null ? "redirect:/list" : "update";
    }
    
    // お問い合わせ削除機能
    @GetMapping("/list/delete/{id}")
    String deleteForm(@PathVariable Long id, Model model) {
        contactService.deleteContact(id);
        return "redirect:/list";
    }
    
}
