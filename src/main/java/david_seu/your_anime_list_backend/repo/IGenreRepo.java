package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenreRepo extends JpaRepository<Genre, Long>{

    Genre findByName(String name);
}
