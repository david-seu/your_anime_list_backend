package david_seu.your_anime_list_backend.service;

import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;

public interface IUserService {

    UserDto createUser(UserDto userDto);

    JwtResponse login(LoginDto loginDto);
}
