package david_seu.your_anime_list_backend.repo;
import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepo  extends JpaRepository<User, Long>{

    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    List<User> findAllByRoleOrderById(ERole role, PageRequest pageRequest);

    List<User> findByUsernameContainingIgnoreCaseOrderByIdAsc(String username, PageRequest pageRequest);

    List<User> findByUsernameContainingIgnoreCaseOrderByIdDesc(String username, PageRequest pageRequest);

}
