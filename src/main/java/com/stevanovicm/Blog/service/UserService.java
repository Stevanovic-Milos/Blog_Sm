package com.stevanovicm.Blog.service;


import com.stevanovicm.Blog.dto.Response;
import com.stevanovicm.Blog.dto.UserDetailsResponse;
import com.stevanovicm.Blog.entity.User.User;
import com.stevanovicm.Blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    //funkcija koja vraca u nasme zahtevanom formatu podatke o korisniku
    public UserDetailsResponse getCurrentUserDetails(User user) {
        //Vracamo korisnika tacno onako kao record zahteva samo ta polja
        return new UserDetailsResponse(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole().toString()
        );
    }
    public Response updateUser(User user, User user_update){
        User userCurrent = userRepository.findById(user.getId()).orElseThrow(()-> new RuntimeException("User not found"));
        userCurrent.setFirstname(user_update.getFirstname());
        userCurrent.setLastname(user_update.getLastname());
        userCurrent.setEmail(user_update.getEmail());
        userCurrent.setUsername(user_update.getUsername());
        userRepository.save(userCurrent);
        return new Response("User updated", true);
    }
}
