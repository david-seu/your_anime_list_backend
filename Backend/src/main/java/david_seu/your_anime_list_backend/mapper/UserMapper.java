package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.model.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto){

        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getEnabled(), ERole.valueOf(userDto.getRole()));
    }

    public static UserDto mapToUserDto(User user){
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getEnabled(), user.getRole().toString());
    }
}
