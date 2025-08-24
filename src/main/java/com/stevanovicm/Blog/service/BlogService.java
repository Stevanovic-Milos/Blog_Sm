package com.stevanovicm.Blog.service;

import com.stevanovicm.Blog.dto.CreateBlogRequest;
import com.stevanovicm.Blog.dto.Response;
import com.stevanovicm.Blog.entity.Blog;
import com.stevanovicm.Blog.entity.User.User;
import com.stevanovicm.Blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
    public Response cronJob(){
        return new Response("Cron job ran", true);
    }

    public Blog getBlogById(int id){
        return blogRepository.findById(id).orElseThrow(()-> new RuntimeException("Blog not found"));
    }

    public Response createBlog(CreateBlogRequest createBlogRequest, User user){
        Blog blog = new Blog();
        blog.setTitle(createBlogRequest.title());
        blog.setCreatedBy(user);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blog.setAuthor(user.getUsername());
        blog.setContent(createBlogRequest.content());
        blog.setCategory(createBlogRequest.category());
        blog.setImageUrl(createBlogRequest.imageUrl());
        blogRepository.save(blog);
        return new Response("Blog created", true);
    }

    public List<Blog> getMyBlogs(User user){
        return blogRepository.findByCreatedBy(user, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
    public Response deleteBlog(int id){
        blogRepository.deleteById(id);
        return new Response("Blog deleted", true);
    }
    public Response editBlog(CreateBlogRequest createBlogRequest){
        Blog blog = blogRepository.findById(createBlogRequest.id()).orElseThrow(()-> new RuntimeException("Blog not found"));
        blog.setTitle(createBlogRequest.title());
        blog.setContent(createBlogRequest.content());
        blog.setCategory(createBlogRequest.category());
        blog.setImageUrl(createBlogRequest.imageUrl());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new Response("Blog edited", true);
    }
}
