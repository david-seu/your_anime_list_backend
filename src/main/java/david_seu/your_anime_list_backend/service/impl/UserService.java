package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.model.ERole;
import david_seu.your_anime_list_backend.model.Role;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.model.User;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;
import david_seu.your_anime_list_backend.repo.IRoleRepo;
import david_seu.your_anime_list_backend.repo.IUserRepo;
import david_seu.your_anime_list_backend.security.jwt.JwtUtils;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    AuthenticationManager authenticationManager;

    IUserRepo userRepo;

    IRoleRepo roleRepo;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    @Override
    public UserDto createUser(UserDto userDto) {

        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = UserMapper.mapToUser(userDto);

        Set<String> strRoles = userDto.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepo.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User savedUser = userRepo.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public JwtResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
