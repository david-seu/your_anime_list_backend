package david_seu.your_anime_list_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    private Long id;

    private String title;

    private String content;

    @MapsId
    @OneToOne
    @JoinColumn(name = "anime_user_id", referencedColumnName = "id")
    private AnimeUser animeUser;
}
