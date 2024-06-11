package com.contact.smc.Form;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotEmpty(message = "fullName is required")
    @Size(min = 4, max = 20, message = "fullName must be between 4 and 20 characters")
    private String fullName;
    @NotEmpty
    @Size(min = 4, max = 20, message = "userName must be between 4 and 20 characters")
    private String name;
    @Email(message = "Please enter a valid email")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Please enter a valid email")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, max = 20, message = "password must be between 4 and 20 characters")
    private String password;
    @Size(max = 10000, message = "about must be less than 10000 characters")
    private String about;
    @Size(max = 10, message = "phoneNo must be less than 10 characters")
    private String phoneNo;
}