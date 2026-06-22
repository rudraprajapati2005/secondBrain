package com.project.secondBrain.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "archive",
    indexes = {
        @Index(name = "idx_archive_title", columnList = "title"),
        @Index(name = "idx_archive_user", columnList = "user_id")
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String url;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private String sourceType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 1024)
    @Column(columnDefinition = "vector(1024)")
    private double[] embedding;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "archive_tags",
        joinColumns = @JoinColumn(name = "archive_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
