package com.unt.mailform;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
