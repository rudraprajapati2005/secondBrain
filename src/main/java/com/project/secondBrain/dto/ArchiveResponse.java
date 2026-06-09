package com.project.secondBrain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
public class ArchiveResponse {
        private Long id;
        private String title;
        private String url;
        private String description;
        private String sourceType;
        private Long userId;
        private String userName;
        private List<String> tags;
        private LocalDateTime createdAt;

}
