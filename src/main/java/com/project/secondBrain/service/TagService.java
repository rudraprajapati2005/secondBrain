package com.project.secondBrain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.secondBrain.entity.Tag;
import com.project.secondBrain.repository.TagRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public Set<Tag> resolveTags(List<Long> tagIds, List<String> tagNames) {
        Set<Tag> tags = new LinkedHashSet<>();

        if (tagIds != null && !tagIds.isEmpty()) {
            tags.addAll(tagRepository.findAllById(tagIds));
        }

        if (tagNames != null) {
            for (String tagName : tagNames) {
                String normalizedTagName = tagName == null ? "" : tagName.trim();
                if (normalizedTagName.isEmpty()) {
                    continue;
                }

                tags.add(tagRepository.findByNameIgnoreCase(normalizedTagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(normalizedTagName);
                            return tagRepository.save(newTag);
                        }));
            }
        }

        return tags;
    }
    
}
