package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;

import java.util.List;

public interface IUserService {

    UserDto signUp(UserDto userDto);

    UserDto signIn(LoginDto loginDto);

    UserDto getUserById(Long userId);

    void verifyUser(String token);

    void sendVerificationEmail(User user);

    void sendLoginCode(User user);

    JwtResponse verifyUser(Integer code);

    List<UserDto> getAllUsers(Integer page, String username, String sort);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);
}
