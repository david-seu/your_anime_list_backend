package david_seu.your_anime_list_backend.payload.dto;

import david_seu.your_anime_list_backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;
    private Set<String> roles;
}
