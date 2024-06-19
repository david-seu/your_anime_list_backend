package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.AnimeSeason;
import david_seu.your_anime_list_backend.model.utils.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnimeSeasonRepo extends JpaRepository<AnimeSeason, Long> {

    AnimeSeason findByYearAndSeason(Integer year, Season season);
}
