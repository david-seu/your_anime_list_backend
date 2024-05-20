package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;

public interface IUserService {

    UserDto signUp(UserDto userDto);

    JwtResponse signIn(LoginDto loginDto);

    User getUserById(Long userId);
}
