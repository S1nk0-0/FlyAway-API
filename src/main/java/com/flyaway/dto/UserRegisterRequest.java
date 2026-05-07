package com.flyaway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRegisterRequest {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*")
    private String firstName;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$")
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
