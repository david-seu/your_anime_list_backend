package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.utils.AnimeStatus;
import david_seu.your_anime_list_backend.model.utils.Type;
import david_seu.your_anime_list_backend.payload.dto.AnimeDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface IAnimeRepo extends JpaRepository<Anime, Long>, JpaSpecificationExecutor<Anime> {

    @Query("select count(a) from Anime a group by a.score")
    List<Integer> findAllGroupByScore();

    Anime findByTitle(String title);

    Anime findByMalId(Integer malId);

    List<Anime> findByType(Type type);
}
