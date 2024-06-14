package david_seu.your_anime_list_backend.model;

import david_seu.your_anime_list_backend.model.utils.AnimeUserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        indexes = {
                @Index(name = "anime_user_id_index", columnList = "user_id"),
                @Index(name = "anime_user_anime_id_index", columnList = "anime_id"),
                @Index(name = "anime_user_score_index", columnList = "score"),
        }
)
public class AnimeUser {

    @EmbeddedId
    private AnimeUserId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @MapsId("animeId")
    @ManyToOne
    @JoinColumn(name = "anime_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Anime anime;

    private Integer score = -1;
    private Boolean isFavorite = false;

    @Enumerated(EnumType.STRING)
    private String status;

    private Date startDate;
    private Date endDate;
}
