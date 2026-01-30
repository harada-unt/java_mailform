package com.unt.mailform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

@Controller
public class CompleteFormController {
    // MailSenderServiceをDIする
    private final MailSenderService mailSenderService;
    private final FormService formService;
    
    public CompleteFormController(MailSenderService mailSenderService, FormService formService) {
        this.mailSenderService = mailSenderService;
        this.formService = formService;
    }

    // 直接URLにアクセスした場合、フォーム入力画面にリダイレクトする
    @GetMapping("/complete")
    public String showCompleteForm(Model model) {       
        model.addAttribute("form", new Form());
        return "index";
    }   

    @PostMapping("/complete")
    // postメソッドでフォームの内容を受け取り、メールを送信する
    public String sendFormMail(
        @Valid @ModelAttribute Form form,
        BindingResult bindingResult,
        Model model
    ) {
        mailSenderService.sendMail(
            form.getName(), 
            form.getEmail(), 
            form.getSubject(), 
            form.getContent() 
        );
        
        // formの内容をデータベースに保存
        com.unt.mailform.Form entityForm = new com.unt.mailform.Form();
        entityForm.setName(form.getName());
        entityForm.setEmail(form.getEmail());
        entityForm.setSubject(form.getSubject());
        entityForm.setContent(form.getContent());
        formService.saveForm(entityForm);
        model.addAttribute("form", form);
        return "complete";
    }
}
