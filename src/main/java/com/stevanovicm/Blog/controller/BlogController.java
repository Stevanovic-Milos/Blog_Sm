package com.stevanovicm.Blog.controller;

import com.stevanovicm.Blog.dto.CreateBlogRequest;
import com.stevanovicm.Blog.dto.Response;
import com.stevanovicm.Blog.entity.Blog;
import com.stevanovicm.Blog.entity.User.User;
import com.stevanovicm.Blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public List<Blog> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @GetMapping("/cron")
    public Response CronJobForRender(){return blogService.cronJob();}

    @PostMapping("/blogs/id")
    public Blog getBlogById(@RequestBody int id){
    return blogService.getBlogById(id);
    }

    @PostMapping("/create")
    public Response createBlog(@RequestBody CreateBlogRequest createBlogRequest, @AuthenticationPrincipal User user){
        return blogService.createBlog(createBlogRequest, user);
    }
    @GetMapping("/my-blogs")
    public List<Blog> getMyBlogs(@AuthenticationPrincipal User user){
        return blogService.getMyBlogs(user);
    }
    @PostMapping("/delete")
    public Response deleteBlogById(@RequestBody int id){
        return blogService.deleteBlog(id);
    }
    @PostMapping("/edit")
    public Response editBlogById(@RequestBody CreateBlogRequest createBlogRequest){
        return blogService.editBlog(createBlogRequest);
    }

}
