package com.unt.mailform.model;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーデータを扱うためのクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    @Column
    @Size(max = 36)
    private String username;

    @NotBlank
    @Column 
    @Size(max = 36)
    private String password;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
}
