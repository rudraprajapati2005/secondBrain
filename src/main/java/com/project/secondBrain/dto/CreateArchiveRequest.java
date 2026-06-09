package com.project.secondBrain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateArchiveRequest {
    private String title;
    private String url;
    private String description;
    private String sourceType;
    private Long userId;
    private List<Long> tagIds;
}
