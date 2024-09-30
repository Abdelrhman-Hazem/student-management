package com.bbyn.studentmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentRegisterRequest {
    @NotBlank(message = "username is required")
    @Size(min = 4, max = 20, message = "username must be between 4 and 20 characters")
    private String username;
    @NotBlank(message = "password is required")
    @Size(min = 4, max = 20, message = "password must be between 4 and 20 characters")
    private String password;
}
