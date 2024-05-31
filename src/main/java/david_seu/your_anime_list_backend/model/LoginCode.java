package david_seu.your_anime_list_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor()
@Setter
@Getter
@ToString
public class LoginCode {
    private static final int EXPIRATION = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Integer code;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    @NonNull
    private User user;

    private Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION * 60 * 1000);
}
