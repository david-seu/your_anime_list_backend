package david_seu.your_anime_list_backend.model.utils;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimeUserId implements Serializable {

    private Long userId;
    private Long animeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeUserId that = (AnimeUserId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(animeId, that.animeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, animeId);
    }
}
