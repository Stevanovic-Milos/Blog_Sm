package com.stevanovicm.Blog.controller;


import com.stevanovicm.Blog.dto.Response;
import com.stevanovicm.Blog.dto.UserDetailsResponse;
import com.stevanovicm.Blog.entity.User.User;
import com.stevanovicm.Blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/UserDetails")
public class UserController {
  private final UserService userService;

  //na adresi /api/UserDetails poyivamo servis koji vadi sve podatke o trenutnom korisniku i vraca u frmatu naseg dto recorda UserDetailsResponse
  @GetMapping
  public ResponseEntity<UserDetailsResponse> getCurrentUserDetails(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok((userService.getCurrentUserDetails(user)));
  }
  @PostMapping("/update")
  public Response updateUser(@AuthenticationPrincipal User user, @RequestBody User user_update){
    return userService.updateUser(user, user_update);
  }
}
