package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.AnimeUser;
import david_seu.your_anime_list_backend.model.Review;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepo extends JpaRepository<Review, Long> {

    List<Review> findByAnimeUser(AnimeUser animeUser, PageRequest pageRequest);
}
