package david_seu.your_anime_list_backend.service.impl;

import david_seu.your_anime_list_backend.exception.EmailAlreadyRegisteredException;
import david_seu.your_anime_list_backend.exception.ResourceNotFoundException;
import david_seu.your_anime_list_backend.exception.UserNotVerifiedException;
import david_seu.your_anime_list_backend.exception.UsernameAlreadyExistsException;
import david_seu.your_anime_list_backend.model.*;
import david_seu.your_anime_list_backend.model.utils.ERole;
import david_seu.your_anime_list_backend.payload.dto.LoginDto;
import david_seu.your_anime_list_backend.payload.dto.UserDto;
import david_seu.your_anime_list_backend.mapper.UserMapper;
import david_seu.your_anime_list_backend.payload.response.JwtResponse;
import david_seu.your_anime_list_backend.repo.*;
import david_seu.your_anime_list_backend.security.jwt.JwtUtils;
import david_seu.your_anime_list_backend.security.service.impl.UserDetailsImpl;
import david_seu.your_anime_list_backend.service.IEmailService;
import david_seu.your_anime_list_backend.service.IUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @NonNull
    private IUserRepo userRepo;

    @NonNull

    private IVerificationTokenRepo verificationTokenRepo;

    @NonNull

    private ILoginCodeRepo loginCodeRepo;

    @NonNull

    private IEmailService mailService;

    @NonNull

    private PasswordEncoder encoder;

    @NonNull

    private AuthenticationManager authenticationManager;

    @NonNull

    private JwtUtils jwtUtils;
    private final Map<Long, JwtResponse> jwtResponseMap = new HashMap<>();

    @Override
    public UserDto signUp(UserDto userDto) {
        UserDto savedUserDto = addUser(userDto);
        sendVerificationEmail(UserMapper.mapToUser(savedUserDto));
        return savedUserDto;
    }

    @Override
    public UserDto signIn(LoginDto loginDto) {
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

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList().get(0);

        JwtResponse response = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);


        jwtResponseMap.put(response.getId(), response);

        sendLoginCode(user);

        return UserMapper.mapToUserDto(user);
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

        return jwtResponseMap.get(loginCode.getUser().getId());


    }

    @Override
    public List<UserDto> getAllUsers(Integer page, String username, String sort) {
        System.out.println(username);
        System.out.println(page);
        List<User> userList;
        if(sort.equals("ASC"))
            userList = userRepo.findByUsernameContainingIgnoreCaseOrderByIdAsc(username, PageRequest.of(page,10));
        else
            userList = userRepo.findByUsernameContainingIgnoreCaseOrderByIdDesc(username, PageRequest.of(page,10));
        return userList.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {

        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Error: Username is already taken!");
        }

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("Error: Email is already in use!");
        }

        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = UserMapper.mapToUser(userDto);

        return UserMapper.mapToUserDto(userRepo.save(user));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found."));

        if (userRepo.existsByUsername(updatedUser.getUsername()) && !user.getUsername().equals(updatedUser.getUsername())) {
            throw new UsernameAlreadyExistsException("Error: Username is already taken!");
        }

        if (userRepo.existsByEmail(updatedUser.getEmail()) && !user.getEmail().equals(updatedUser.getEmail())) {
            throw new EmailAlreadyRegisteredException("Error: Email is already in use!");
        }

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(encoder.encode(updatedUser.getPassword()));
        user.setRole(ERole.valueOf(updatedUser.getRole()));

        User updatedUserObj = userRepo.save(user);

        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found."));

        userRepo.delete(user);
    }


    public UserDto getUserById(Long userId) {
        return UserMapper.mapToUserDto(userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Error: User not found.")));
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
