package com.stevanovicm.Blog.dto;

public record CreateBlogRequest(String title, String content, String category, String imageUrl, Integer id) {
}
