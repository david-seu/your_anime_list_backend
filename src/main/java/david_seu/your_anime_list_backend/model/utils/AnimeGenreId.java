package david_seu.your_anime_list_backend.model.utils;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AnimeGenreId implements java.io.Serializable{

    private Long animeId;
    private Long genreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeGenreId that = (AnimeGenreId) o;
        return animeId.equals(that.animeId) && genreId.equals(that.genreId);
    }

    @Override
    public int hashCode() {
        return animeId.hashCode() + genreId.hashCode();
    }
}
