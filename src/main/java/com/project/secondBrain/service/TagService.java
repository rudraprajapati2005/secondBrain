package com.project.secondBrain.service;

import org.springframework.stereotype.Service;
import com.project.secondBrain.entity.Tag;
import com.project.secondBrain.repository.TagRepository;
import java.util.List;
@Service
public class TagService {
     private final TagRepository tagRepository;
    public TagService(TagRepository tagRepository) {    
        this.tagRepository = tagRepository;
    }
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    
}
