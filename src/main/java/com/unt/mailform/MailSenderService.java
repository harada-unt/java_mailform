package com.unt.mailform;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;   
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class MailSenderService {

    /**
     * メール送信のクラスをDIする
     */
    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.properties.admin.email}")
    private String adminEmail;

    public void sendMail (String name, String email, String subject, String content) {
        // 送信するメール内容を作成し設定する。
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail); // 送信先メールアドレスを設定する
        message.setFrom(email) ; // 送信元メールアドレスを設定する
        message.setSubject(subject);


    // テンプレートエンジンを使用するための設定インスタンスを作成
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    // classpath:/templates/ をプレフィックスとして使い、.html をサフィックスに設定
    templateResolver.setPrefix("mail/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);

    // テンプレートエンジンを使用すためのインスタンスを作成
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
        
        // メールテンプレートに設定するパラメータを設定する。
        // パラメータformに入力した内容を設定する。
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("subject", subject);
        variables.put("email", email);
        variables.put("content", content);

    // テンプレート名はサフィックスなしで指定する
    String text = templateEngine.process("mailTemplate", new Context(null, variables));
        message.setText(text);
        this.mailSender.send(message);
    }

}
