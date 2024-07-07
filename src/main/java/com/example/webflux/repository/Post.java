package com.example.webflux.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    private String title;
    private String content;
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
