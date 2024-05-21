package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.exception.EmailAlreadyRegisteredException;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.exception.UserNotVerifiedException;
import david_seu.your_anime_list_backend.exception.UsernameAlreadyExistsException;
import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;
import david_seu.your_anime_list_backend.repo.*;
import david_seu.your_anime_list_backend.security.jwt.JwtUtils;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IEmailService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private IUserRepo userRepo;

    private IRoleRepo roleRepo;

    private IVerificationTokenRepo verificationTokenRepo;

    private ILoginCodeRepo loginCodeRepo;

    private IEmailService mailService;

    private PasswordEncoder encoder;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private RedisTemplate<Long, Object> redisTemplate;

    @Override
    public User signUp(UserDto userDto) {

        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Error: Username is already taken!");
        }

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("Error: Email is already in use!");
        }

        userDto.setPassword(encoder.encode(userDto.getPassword()));
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
        sendVerificationEmail(user);
        return userRepo.save(user);
    }

    @Override
    public User signIn(LoginDto loginDto) {
        User user = userRepo.findByUsername(loginDto.getUsername());

        if(user == null)
        {
            throw new ResourceNotFoundException("Error: User not found.");
        }

        if (!user.getEnabled()) {
            throw new UserNotVerifiedException("Error: User not verified.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtResponse response = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);


        redisTemplate.opsForValue().set(response.getId(), response);

        sendLoginCode(user);

        return user;
    }

    @Override
    public void verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
        if (verificationToken == null) {
            throw new ResourceNotFoundException("Error: Verification token not found.");
        }


        if(isExpired(verificationToken.getExpiryDate()))
        {
            throw new ResourceNotFoundException("Error: Verification token expired.");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepo.save(user);

        verificationTokenRepo.delete(verificationToken);
    }

    @Override
    public JwtResponse verifyUser(Integer code) {
        LoginCode loginCode = loginCodeRepo.findByCode(code);
        if (loginCode == null) {
            throw new ResourceNotFoundException("Error: Login code not found.");
        }

        if(isExpired(loginCode.getExpiryDate()))
        {
            throw new ResourceNotFoundException("Error: Login code expired.");
        }



        loginCodeRepo.delete(loginCode);

        return (JwtResponse) redisTemplate.opsForValue().get(loginCode.getUser().getId());


    }


    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found."));
    }


    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);

        verificationTokenRepo.save(verificationToken);
    }

    public void createLoginCode(User user, Integer code) {
        LoginCode loginCode = new LoginCode(code, user);

        loginCodeRepo.save(loginCode);
    }

    @Override
    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String message = "Confirm your email by entering the following code: " + token;

        mailService.sendEmail(recipientAddress, subject, message);
    }

    @Override
    public void sendLoginCode(User user) {
        int code = new Random().nextInt(999999) + 100000;
        createLoginCode(user, code);

        String recipientAddress = user.getEmail();
        String subject = "Login Code";
        String message = "Enter the following code to login: " + code;

        mailService.sendEmail(recipientAddress, subject, message);
    }

    private boolean isExpired(Date expiryDate) {
        Calendar cal = Calendar.getInstance();
        return (expiryDate.getTime() - cal.getTime().getTime()) <= 0;
    }
}
