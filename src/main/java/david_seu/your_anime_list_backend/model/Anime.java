package david_seu.your_anime_list_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        indexes = {
                @Index(name = "anime_title_index", columnList = "title"),
                @Index(name = "anime_score_index", columnList = "score"),
                @Index(name = "anime_user_id_index", columnList = "user_id")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Anime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer score = -1;
    private Boolean watched = false;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
