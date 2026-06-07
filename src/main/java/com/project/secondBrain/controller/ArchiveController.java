package com.project.secondBrain.controller;
import com.project.secondBrain.service.ArchiveService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.secondBrain.entity.*;
import java.util.List;

@RestController
@RequestMapping("/api/archives")
public class ArchiveController {
    private final ArchiveService archiveService;
    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @PostMapping
    public Archive createArchive(@RequestBody Archive archive) {
        return archiveService.saveArchive(archive);
    }

    @GetMapping
    public List<Archive> getAllArchives() {
        return archiveService.getAllArchives();
    }


}
