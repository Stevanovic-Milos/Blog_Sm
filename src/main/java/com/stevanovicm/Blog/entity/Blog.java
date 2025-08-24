package com.stevanovicm.Blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stevanovicm.Blog.entity.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BLOG")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(length = 50000)
    private String content;
    private String author;
    private String category;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;


}
