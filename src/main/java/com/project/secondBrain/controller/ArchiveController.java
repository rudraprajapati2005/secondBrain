package com.project.secondBrain.controller;

import com.project.secondBrain.service.ArchiveService;
import com.project.secondBrain.dto.ArchiveResponse;
import com.project.secondBrain.dto.CreateArchiveRequest;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/archives")
public class ArchiveController {
    private final ArchiveService archiveService;

    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @PostMapping
    public ArchiveResponse createArchive(@Valid @RequestBody CreateArchiveRequest archive) {
        return archiveService.createArchive(archive);
    }

    @GetMapping
    public List<ArchiveResponse> getAllArchives() {
        return archiveService.getAllArchives();
    }

    @GetMapping("/test")
    public List<ArchiveResponse> test() {
        return List.of(
            ArchiveResponse.builder()
                .id(1L)
                .title("Test Archive")
                .url("http://example.com")
                .description("Sample")
                .sourceType("WEB")
                .userId(99L)
                .userName("Rudra")
                .tags(List.of("tag1", "tag2"))
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ArchiveResponse getArchiveById(@PathVariable Long id) {
        return archiveService.getArchiveById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteArchive(@PathVariable Long id) {
        archiveService.deleteArchive(id);
    }

    @PutMapping("/{id}")
    public ArchiveResponse updateArchive(@PathVariable Long id, @Valid @RequestBody CreateArchiveRequest archive) {
        return archiveService.updateArchive(id, archive);
    }

    @GetMapping("/page")
    public Page<ArchiveResponse> getArchives(Pageable pageable) {
        return archiveService.getArchives(pageable);
    }

    @GetMapping("/search")
    public Page<ArchiveResponse> searchArchives(@RequestParam String keyword, Pageable pageable) {
        return archiveService.findByTitleContainingIgnoreCase(keyword, pageable);
    }
}
