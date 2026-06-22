package com.project.secondBrain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String email;  
    
    //Constructors
    public LoginResponse(String token) {
        this.token = token;
    }
    public LoginResponse(String email,String token)
    {
        this.email = email;
        this.token = token;
    }
    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
