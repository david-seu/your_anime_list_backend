package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.model.Role;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
