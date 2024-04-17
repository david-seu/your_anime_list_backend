package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.dto.EpisodeDto;
import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEpisodeRepo extends JpaRepository<Episode, Long> {

    List<Episode> getEpisodesByAnime(Anime anime);

}
