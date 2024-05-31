package david_seu.your_anime_list_backend.repo;

import david_seu.your_anime_list_backend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}
