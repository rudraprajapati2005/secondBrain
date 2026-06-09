package com.project.secondBrain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.secondBrain.service.TagService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.project.secondBrain.entity.Tag;
@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @PostMapping("/tags")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }
    @GetMapping("/tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

}
