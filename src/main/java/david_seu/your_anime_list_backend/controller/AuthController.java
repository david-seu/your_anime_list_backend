package david_seu.your_anime_list_backend.controller;

import java.util.HashSet;
import java.util.Set;

import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;
import david_seu.your_anime_list_backend.service.IUserService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    IUserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

        JwtResponse jwt = userService.login(loginDto);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {

        try{
            UserDto user = userService.createUser(userDto);
            return ResponseEntity.ok(user);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
