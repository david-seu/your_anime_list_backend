package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;

public interface IUserService {

    User signUp(UserDto userDto);

    User signIn(LoginDto loginDto);

    User getUserById(Long userId);

    void verifyUser(String token);

    void sendVerificationEmail(User user);

    void sendLoginCode(User user);

    JwtResponse verifyUser(Integer code);

}
