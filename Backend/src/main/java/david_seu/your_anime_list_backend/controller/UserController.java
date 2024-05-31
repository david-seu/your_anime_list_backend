package david_seu.your_anime_list_backend.controller;

import david_seu.your_anime_list_backend.exception.EmailAlreadyRegisteredException;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.exception.UsernameAlreadyExistsException;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    IUserService userService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, defaultValue = "DESC") String sort, @RequestParam(required = false, defaultValue = "") String username, @RequestParam(required = false, defaultValue = "0") Integer page) {
        try {
            List<UserDto> usersList = userService.getAllUsers(page, username, sort);
            if (usersList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long userId){
        try {

            UserDto userDto = userService.getUserById(userId);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        try {
            UserDto saveUserDto = userService.addUser(userDto);
            return new ResponseEntity<>(saveUserDto, HttpStatus.CREATED);
        }
        catch (UsernameAlreadyExistsException | EmailAlreadyRegisteredException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long userId, @RequestBody UserDto updatedUser)
    {
        try {
            UserDto userDto = userService.updateUser(userId, updatedUser);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (UsernameAlreadyExistsException | EmailAlreadyRegisteredException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long userId)
    {
        try {
            userService.deleteUser(userId);
        }
        catch(ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
