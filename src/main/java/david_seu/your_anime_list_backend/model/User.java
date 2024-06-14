package david_seu.your_anime_list_backend.model;

import david_seu.your_anime_list_backend.model.utils.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "`user`",
    indexes = {
            @Index(name = "user_username_index", columnList = "username")
    },
    uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    private Boolean enabled = false;

    private Date joinDate;

    @Column(name = "role")
    private ERole role;
}
