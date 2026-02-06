package com.unt.mailform;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@Controller
public class MailformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailformApplication.class, args);
	}

	@GetMapping("/")
	public String ContactForm(Model model) {
		model.addAttribute("contactForm", new ContactForm());
		return "index";
	}

	// ページテンプレート用のテンプレートエンジンを設定する
	@Primary
	@Bean
	public SpringTemplateEngine pageTemplateEngine() {
		var resolver = new ClassLoaderTemplateResolver();
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setPrefix("templates/");
		resolver.setSuffix(".html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(true);
		resolver.setOrder(1);
		var engine = new SpringTemplateEngine();
		engine.setTemplateResolver(resolver);
		return engine;
	}

	// メールテンプレート用のテンプレートエンジンを設定する
	@Bean("messageTemplateEngine")
	public SpringTemplateEngine messageTemplateEngine() {
		var resolver = new ClassLoaderTemplateResolver();
		resolver.setTemplateMode(TemplateMode.TEXT);
		resolver.setPrefix("templates/email/");
		resolver.setSuffix(".txt");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(true);
		var engine = new SpringTemplateEngine();
		engine.setTemplateResolver(resolver);
		return engine;
	}
}
