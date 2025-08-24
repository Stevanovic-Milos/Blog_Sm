package com.stevanovicm.Blog.repository;

import com.stevanovicm.Blog.entity.Blog;
import com.stevanovicm.Blog.entity.User.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findByCreatedBy(User createdBy, Sort createdAt);
}
