package com.unt.mailform;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfirmFormController {
    // 直接URLにアクセスした場合、フォーム入力画面にリダイレクトする
    @GetMapping("/confirm")
    public String showForm(Model model) {
        model.addAttribute("form", new Form());
        return "index";
    }

    @PostMapping("/confirm")
    public String submitForm(
        @Valid @ModelAttribute Form form, 
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、入力画面に戻る
            return "index";
        }
        model.addAttribute("form", form);
        return "confirm";
    }
}