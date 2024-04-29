package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Anime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAnimeRepo extends JpaRepository<Anime, Long> {

    List<Anime> findAnimeByOrderByScore(PageRequest pageRequest);

}
