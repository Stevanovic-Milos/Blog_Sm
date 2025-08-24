package com.stevanovicm.Blog.auth;

import com.stevanovicm.Blog.dto.AuthResponse;
import com.stevanovicm.Blog.dto.LoginRequest;
import com.stevanovicm.Blog.dto.RegisterRequest;
import com.stevanovicm.Blog.entity.User.User;
import com.stevanovicm.Blog.entity.User.UserRoles;
import com.stevanovicm.Blog.repository.UserRepository;
import com.stevanovicm.Blog.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  // radimo dependenci injection na moderan nacin
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  //signup funkcija zaduzena za upis korisnika u bazu poziva s eu kontroleru
  public AuthResponse signup(RegisterRequest registerRequest) {
    //provera postojanja username koji je korisnik uneo pri registraciji u bazi funkcija je deklarisana u UserRepository
    if (userRepository.existsByUsername(registerRequest.username())) {
      //ovaj return govori nasem frontu koju poruku da prikaze
      return new AuthResponse("username-exists",false);
    }

    //provera postojanja emaila koji je korisnik uneo u bazi funkcija je deklarisana u UserRepository
    if (userRepository.existsByEmail(registerRequest.email())) {
      //ovaj return govori nasem frontu koju poruku da prikaze
      return new AuthResponse("email-exists",false);
    }
    //kreiramo korisnika
    var user = User.builder()
      .username(registerRequest.username())
      .password(passwordEncoder.encode(registerRequest.password()))
      .firstname(registerRequest.firstname())
      .lastname(registerRequest.lastname())
      .email(registerRequest.email())
      .role(UserRoles.ROLE_USER)
      .build();

    //pozivamo jpa funkciju iz naseg userRepositorija za upis korisnika u abzu
    userRepository.save(user);
    //mogli smo da napravimo token ali elegantnije je da samo signin bude za to zaduzen pa cmeo an frontu pozivati signin
    //takodje ovo resava problem logovanja korisnika ako slucajno na signup-u unese podatke postojeceg korisnika (tacno username i sifru)
    //da ovde vratimo token uspesno bismo ga ulogovali sto je veoma loasa praksa
    return new AuthResponse("signed-up", true);
  }

  //funkcija za signin korisnika
  public AuthResponse signin(LoginRequest loginRequest) {
    //preko signin menagera kreira spring security token koji proverava postojanje korisnika sa tacnim username i sifrom
    //ako tacno poklapanje ne postoji ovo ce vrtiti gresku 401 a korisniku ne mormao prikazivati uopste sta od podataka nije tacno
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.username(),
        loginRequest.password()
      )
    );
    //kreiramo korisnika preko nase repo funkcije koji vraca ceo objekat korisnika
    var user = userRepository.findByUsername(loginRequest.username())
      .orElseThrow(()-> new RuntimeException("User not found"));
    //preko naseg jwtUtila generismo Jwttoken
    var JwtToken = jwtUtil.generateToken(user);
    //na krjau vracamo jwtToken i status true sto an frontyu koristimo da propustimo korisnika u aplikaciju
    return new AuthResponse(JwtToken, true);
  }
}
