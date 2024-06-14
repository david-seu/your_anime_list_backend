package david_seu.your_anime_list_backend.model;

import david_seu.your_anime_list_backend.model.utils.AnimeGenreId;
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
public class AnimeGenre {

    @EmbeddedId
    private AnimeGenreId id;

    @ManyToOne
    @MapsId("animeId")
    private Anime anime;

    @ManyToOne
    @MapsId("genreId")
    private Genre genre;
}
