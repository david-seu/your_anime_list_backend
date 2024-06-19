package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Anime;
import david_seu.your_anime_list_backend.model.AnimeUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnimeUserRepo extends JpaRepository<AnimeUser, Long> {

    List<AnimeUser> findByUserId(Long userId, PageRequest pageRequest);

    AnimeUser findByAnimeIdAndUserId(Long animeId, Long userId);

    void deleteByAnimeIdAndUserId(Long animeId, Long userId);

    @Query("SELECT au FROM AnimeUser au WHERE au.user.id = :userId AND lower(au.anime.title) LIKE lower(concat('%', :title, '%')) ORDER BY au.anime.title ASC")
    List<AnimeUser> findByAnimeTitleContainingIgnoreCaseOrderByScoreAsc(Long userId, String title, PageRequest pageRequest);

    @Query("SELECT au FROM AnimeUser au WHERE au.user.id = :userId AND lower(au.anime.title) LIKE lower(concat('%', :title, '%')) ORDER BY au.anime.title DESC")
    List<AnimeUser> findByAnimeTitleContainingIgnoreCaseOrderByScoreDesc(Long userId, String title, PageRequest pageRequest);


}
