package com.project.secondBrain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import  jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateArchiveRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "URL is required")  
    private String url;
    

    private String description;

    @NotBlank(message = "Source type is required")
    private String sourceType;


    private List<Long> tagIds = new ArrayList<>();

    private List<String> tagNames = new ArrayList<>();
}
