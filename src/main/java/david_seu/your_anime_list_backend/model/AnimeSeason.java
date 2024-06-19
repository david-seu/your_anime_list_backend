package david_seu.your_anime_list_backend.model;


import david_seu.your_anime_list_backend.model.utils.Season;
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
@Table(
        indexes = {
                @Index(name = "anime_season_year_index", columnList = "year")
        }
)
public class AnimeSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private Season season;
}
