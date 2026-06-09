package com.project.secondBrain.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "archive")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Archive {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String title;

@Column(nullable = false, length = 2000)
private String url;

@Column(nullable = true , length = 5000)
private String description;

@Column(nullable = false)
private String sourceType;

@Column(nullable = false)
private LocalDateTime createdAt;

@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();

}

@JsonIgnore
@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;

@JsonIgnore
@ManyToMany
@JoinTable(
        name = "archive_tags",
        joinColumns = @JoinColumn(name = "archive_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
)
private Set<Tag> tags = new HashSet<>();
}
