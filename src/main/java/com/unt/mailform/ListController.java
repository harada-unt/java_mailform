package com.unt.mailform;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ListController {

    @Autowired
    private FormService formService;

    @GetMapping("/list")
    public String showList(Model model) {
        List<Form> forms = formService.getContactForm();
        model.addAttribute("forms", forms);
        return "list";
    }

    @PostMapping("/list")
    public String searchList(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "email", required = false) String email,
        Model model) {
            String keyword = null;
            if (name != null && !name.isBlank()) {
                keyword = name;
            } else if (email != null && !email.isBlank()) {
                keyword = email;
            }

            List<Form> forms;

            if (keyword == null) {
                // キーワードが未指定の場合は全件取得
                forms = formService.getContactForm();
            } else {
                forms = formService.searchContact(keyword);
            }
            
            if (forms.isEmpty()) {
                // 検索キーワードが無かった場合
                model.addAttribute("message", "該当するデータが見つかりませんでした。");
            }

            model.addAttribute("forms", forms);
            return "list";
        }

    @GetMapping("/list/update/{id}")
    String updateForm(@PathVariable Integer id, Model model) {
        Form form = formService.getContactFormById(id);
        model.addAttribute("form", form);
        return "update";
    }

    @PostMapping("/list/update/{id}")
    String updateForm(@PathVariable Integer id, @Valid 
        Form form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、入力画面に戻る
            return "update";
        }
        form.setId(id.longValue());
        return formService.updateForm(id, form) != null ? "redirect:/list" : "error";
    }

    @GetMapping("/list/delete/{id}")
    String deleteForm(@PathVariable Integer id) {
        formService.deleteForm(id);
        return "redirect:/list";
    }
}
