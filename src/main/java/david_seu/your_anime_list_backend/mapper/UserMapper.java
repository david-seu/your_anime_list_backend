package david_seu.your_anime_list_backend.mapper;

import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.model.Role;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), new HashSet<>());
    }

    public static UserDto mapToUserDto(User user){
        Set<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), roles);
    }
}
