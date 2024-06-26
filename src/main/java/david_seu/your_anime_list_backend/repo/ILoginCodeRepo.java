package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.LoginCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginCodeRepo extends JpaRepository<LoginCode, Long> {

    LoginCode findByCode(Integer code);
}
