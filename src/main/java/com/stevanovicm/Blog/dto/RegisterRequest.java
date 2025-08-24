package com.stevanovicm.Blog.dto;

//podaci koje ocekujemo na sign-up
public record RegisterRequest(String username, String password, String email, String firstname, String lastname) {}
