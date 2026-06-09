package com.project.secondBrain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
