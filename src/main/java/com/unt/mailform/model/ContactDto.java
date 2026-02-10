package com.unt.mailform.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * お問い合わせフォームクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private Long id;

    @NotBlank(message = "{mailform.name.notblank}")
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "{mailform.name.max}")    
    private String name;

    @NotBlank(message = "{mailform.email.notblank}")
    @Column(nullable = false, length = 100)
    @Email(message = "{mailform.email.invalid}")
    @Size(max = 100, message = "{mailform.email.max}")
    private String email;

    @NotBlank(message = "{mailform.subject.notblank}") 
    @Column(nullable = false, length = 100)
    @Size(max = 100, message = "{mailform.subject.max}")
    private String subject;

    @NotBlank(message = "{mailform.content.notblank}")
    @Column(nullable = false, length = 255)
    @Size(max = 255, message = "{mailform.content.max}")
    private String content;
    
}
