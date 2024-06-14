package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.Genre;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAnimeRepo extends JpaRepository<Anime, Long> {

    @Query("select count(a) from Anime a group by a.score")
    List<Integer> findAllGroupByScore();

    Anime findByTitle(String title);

    List<Anime> findByGenre(Genre genre);

    List<Anime> findByTitleContainingIgnoreCaseOrderByIdAsc(String title, PageRequest pageRequest);
    List<Anime> findByTitleContainingIgnoreCaseOrderByIdDesc(String title, PageRequest pageRequest);

}
