package com.unt.mailform;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class MailformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailformApplication.class, args);
	}

	@GetMapping("/")
	public String ContactForm(Model model) {
		model.addAttribute("form", new Form());
		return "index";
	}
}
