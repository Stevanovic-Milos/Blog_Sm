package com.stevanovicm.Blog.entity.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

// generiše gettere, settere, toString, equals, hashCode
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")

public class User implements UserDetails {
  @Id // Označava primarni ključ
  @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto-increment u bazi
  private Long id;

  private String username;

  private String password; // Obična kolona bez dodatnih ograničenja

  private String firstname;

  private String lastname;

  private String email;

  @Enumerated(EnumType.STRING)
  private UserRoles role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }
}
