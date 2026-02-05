package com.unt.mailform;
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
        model.addAttribute("contactForm", new ContactForm());
        return "index";
    }
    @PostMapping("confirm")
    public String submitForm(
        @Valid @ModelAttribute ContactForm contactForm,
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
        model.addAttribute("form", new ContactForm());
        return "index";
    }
    @PostMapping("/complete")
    public String sendFormMail(
        @Valid @ModelAttribute ContactForm contactForm,
        BindingResult bindingResult,
        Model model
    ) {
        // メールを送信できなかった時の例外処理
        try {
            mailSenderService.sendMail(
                contactForm.getName(),
                contactForm.getEmail(),
                contactForm.getSubject(),
                contactForm.getContent()
            );
        } catch (Exception e) {
            model.addAttribute("message", "メールの送信に失敗しました。時間をおいて再度お試しください。");
            return "error";
        }

        contactService.saveContact(contactForm);
        return "complete";
    }

    // 一覧表示と検索機能
    @GetMapping("/list")
    public String showList(Model model) {
        List<Contact> contacts = contactService.getContact();
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactForm()", new ContactForm());
        return "list";
    }
    @PostMapping("/list")
    public String searchList(
    @RequestParam(name = "name", required = false) String name,
    @RequestParam(name = "email", required = false) String email,
    Model model) {
        String keyword = null;
        if (name != null && !name.isBlank()) {
            keyword =name;
        } else if (email != null && !email.isBlank()) {
            keyword = email;
        }

        List<Contact> contacts;

        if (keyword == null) {
            // キーワードが未指定の場合は全件取得する
            contacts = contactService.getContact();
        } else {
            contacts = contactService.searchContact(keyword);
        }
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactForm", new ContactForm());
        return "list";
    }

    // お問い合わせ編集機能
    @GetMapping("/list/update/{id}")
    String updateContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        ContactForm contactForm =new ContactForm();
        contactForm.setId(contact.getId());
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setSubject(contact.getSubject());
        contactForm.setContent(contact.getContent());
        model.addAttribute("contactForm", contactForm);
        return "update";
    }
    @PostMapping("/list/update/{id}")
    String updateForm(@PathVariable Long id, @Valid ContactForm contactForm,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        return contactService.updateContact(id, contactForm) != null ? "redirect:/list" : "update";
    }
    
    // お問い合わせ削除機能
    @GetMapping("/list/delete/{id}")
    String deleteForm(@PathVariable Long id, Model model) {
        contactService.deleteContact(id);
        return "redirect:/list";
    }
    
}
