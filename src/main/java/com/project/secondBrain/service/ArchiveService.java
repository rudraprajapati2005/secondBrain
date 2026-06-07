package com.project.secondBrain.service;
import com.project.secondBrain.repository.ArchiveRepository;
import com.project.secondBrain.entity.Archive;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArchiveService {
    private final ArchiveRepository archiveRepository;
    public ArchiveService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;

    }
    public Archive saveArchive(Archive archive) {
        return archiveRepository.save(archive);
    }

    public List<Archive> getAllArchives(){
        return archiveRepository.findAll();
    }
}
