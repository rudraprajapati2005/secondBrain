package com.project.secondBrain.repository;

import java.util.List;
import com.project.secondBrain.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    List<Archive> findByTitleContainingIgnoreCase(String keyword);
}
