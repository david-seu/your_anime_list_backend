package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudioRepo extends JpaRepository<Studio, Long> {

    Studio findByName(String name);
}
