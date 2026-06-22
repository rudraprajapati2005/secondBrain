package com.project.secondBrain.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
