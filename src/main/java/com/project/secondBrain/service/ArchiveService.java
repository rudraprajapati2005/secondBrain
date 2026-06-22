package com.project.secondBrain.service;

import com.project.secondBrain.repository.ArchiveRepository;
import com.project.secondBrain.entity.Archive;
import org.springframework.stereotype.Service;

import com.project.secondBrain.repository.UserRepository;
import com.project.secondBrain.dto.ArchiveResponse;
import com.project.secondBrain.dto.CreateArchiveRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.project.secondBrain.entity.Tag;
import com.project.secondBrain.entity.User;
import com.project.secondBrain.entity.Archive;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ArchiveService {
    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final UserService userService;
    private final EmbeddingService emService;

    public ArchiveService(ArchiveRepository archiveRepository,
                          UserRepository userRepository,
                          TagService tagService,
                          UserService userService,
                          EmbeddingService emService
                        ) {
        this.archiveRepository = archiveRepository;
        this.userRepository = userRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.emService = emService;
    }

    private ArchiveResponse toResponse(Archive archive) {
        return ArchiveResponse.builder()
                .id(archive.getId())
                .title(archive.getTitle())
                .url(archive.getUrl())
                .description(archive.getDescription())
                .sourceType(archive.getSourceType())
                .userId(archive.getUser().getId())
                .userName(archive.getUser().getName())
                .tags(archive.getTags().stream().map(Tag::getName).toList())
                .createdAt(archive.getCreatedAt())
                .build();
    }

    @CacheEvict(value = "archives", allEntries = true)
    public ArchiveResponse createArchive(CreateArchiveRequest request) {
        String email = userService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var tags = tagService.resolveTags(request.getTagIds(), request.getTagNames());
        double[] embeddings =   emService.generateEmbedding(request.getTitle() + " " + request.getDescription() + " " + request.getUrl());
        Archive archive = Archive.builder()
                .title(request.getTitle())
                .url(request.getUrl())
                .description(request.getDescription())
                .sourceType(request.getSourceType())
                .user(user)
                .tags(tags)
                .embedding(embeddings)
                .build();

        Archive savedArchive = archiveRepository.save(archive);
        return toResponse(savedArchive);
    }

    @Cacheable(value = "archives", key = "@userService.getCurrentUserEmail()")
    public List<ArchiveResponse> getAllArchives() {
        String email = userService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return archiveRepository.findByUser(user).stream()
                .map(this::toResponse)
                .toList();
    }

    public ArchiveResponse getArchiveById(Long id) {
        Archive archive = archiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archive not found"));
        return toResponse(archive);
    }

    @CacheEvict(value = "archives", allEntries = true)
    public void deleteArchive(Long id) {
        Archive ar = archiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archive not found"));
        String email = userService.getCurrentUserEmail();

        if (!ar.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to delete this archive");
        }
        archiveRepository.deleteById(id);
    }

    @CacheEvict(value = "archives", allEntries = true)
    public ArchiveResponse updateArchive(Long id, CreateArchiveRequest request) {
        Archive archive = archiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archive not found"));

        String email = userService.getCurrentUserEmail();
        if (!archive.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to update this archive");
        }
         double[] embeddings =   emService.generateEmbedding(request.getTitle() + " " + request.getDescription() + " " + request.getUrl());
        var tags = tagService.resolveTags(request.getTagIds(), request.getTagNames());
        archive.setTitle(request.getTitle());
        archive.setUrl(request.getUrl());
        archive.setDescription(request.getDescription());
        archive.setSourceType(request.getSourceType());
        archive.setTags(tags);
        archive.setEmbedding(embeddings);
        Archive updatedArchive = archiveRepository.save(archive);
        return toResponse(updatedArchive);
    }

    public Page<ArchiveResponse> getArchives(Pageable pageable) {
        return archiveRepository.findAll(pageable).map(this::toResponse);
    }

    public Page<ArchiveResponse> findByTitleContainingIgnoreCase(String keyword, Pageable pageable) {
        return archiveRepository.findByTitleContainingIgnoreCase(keyword, pageable).map(this::toResponse);
    }
}
