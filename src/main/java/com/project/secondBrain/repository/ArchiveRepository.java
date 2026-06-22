package com.project.secondBrain.repository;

import java.util.List;
import com.project.secondBrain.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.secondBrain.entity.User;
import org.springframework.data.repository.query.Param;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    List<Archive> findByTitleContainingIgnoreCase(String keyword);
    Page<Archive> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    List<Archive> findByUser(User user);

    @Query(value = """
            SELECT * FROM archive 
            WHERE user_id = :userId
            ORDER BY embedding <=> CAST(:embedding AS vector)
            LIMIT :limit
            """,nativeQuery = true)
        List<Archive> findMostSimilarForUser(@Param("userId") Long userId , @Param("embedding") String embedding, @Param("limit") int limit );
}
