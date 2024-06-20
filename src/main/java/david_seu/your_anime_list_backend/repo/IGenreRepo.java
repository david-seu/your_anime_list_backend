package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGenreRepo extends JpaRepository<Genre, Long> {

        Genre findFirstByNameIgnoreCase(String name);
}
