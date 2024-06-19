package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepo extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
