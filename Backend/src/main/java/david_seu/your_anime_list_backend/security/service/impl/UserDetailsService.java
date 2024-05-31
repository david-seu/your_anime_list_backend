package david_seu.your_anime_list_backend.security.service.impl;

import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.repo.IUserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    IUserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return UserDetailsImpl.build(user);
    }
}
