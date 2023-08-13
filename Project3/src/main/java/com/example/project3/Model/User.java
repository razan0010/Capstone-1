package com.example.project3.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "ID must be not empty!")
    private Integer id;

    @NotEmpty(message = "Username must be not empty!")
    @Size(min = 5, message = "Length of username must be more than 5")
    private String username;

    @NotEmpty(message = "Password must be not empty!")
    @Size(min = 6, message = "Length of password must be more than 6")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\s]{1,}$" , message = "Password have characters and digits")
    private String password;

    @NotEmpty(message = "Email must be not empty!")
    @Pattern(regexp ="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" , message = "Email must be a valid")
    private String email;

    @NotEmpty(message = "Role must be not empty!")
    @Pattern(regexp ="\\b(Admin|Customer)\\b" , message = "Role must have to be in (“Admin”,”Customer”)")
    private String role;

    @NotNull(message = "Balance must be not empty!")
    @Positive(message = "Balance must be positive!")
    private Double balance;
}
