package david_seu.your_anime_list_backend.model;

import david_seu.your_anime_list_backend.model.utils.WatchStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(
        indexes = {
                @Index(name = "anime_user_id_index", columnList = "user_id"),
                @Index(name = "anime_user_anime_id_index", columnList = "anime_id"),
                @Index(name = "anime_user_score_index", columnList = "score"),
        }
)
public class AnimeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "anime_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Anime anime;

    private Double score = -1.0;
    private Boolean isFavorite = false;

    @Enumerated(EnumType.STRING)
    private WatchStatus status;

    private Date startDate;
    private Date endDate;
}