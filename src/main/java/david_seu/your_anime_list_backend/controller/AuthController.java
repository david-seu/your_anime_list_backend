package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.exception.UserNotVerifiedException;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;
import david_seu.your_anime_list_backend.service.IUserService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    IUserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            UserDto user = UserMapper.mapToUserDto(userService.signIn(loginDto));
            return ResponseEntity.ok(user);
        }
        catch (UserNotVerifiedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try{
            User user = userService.signUp(userDto);
            return ResponseEntity.ok(user+ " Now check your email to confirm registration");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/signUp/confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        try{
            userService.verifyUser(token);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return new ResponseEntity<>("User created",HttpStatus.CREATED);
    }

    @GetMapping("/signIn/confirm")
    public ResponseEntity<?> confirmLogin(@RequestParam("code") Integer code) {
        try{
            JwtResponse response = userService.verifyUser(code);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
