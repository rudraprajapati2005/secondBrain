package com.project.secondBrain.repository;

import com.project.secondBrain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByNameIgnoreCase(String name);
}