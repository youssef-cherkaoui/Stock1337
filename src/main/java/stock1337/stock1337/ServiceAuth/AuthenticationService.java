package stock1337.stock1337.ServiceAuth;


import stock1337.stock1337.DTO.AuthenticationRequest;
import stock1337.stock1337.DTO.AuthenticationResponse;
import stock1337.stock1337.DTO.RegisterRequest;
import stock1337.stock1337.Enums.Role;
import stock1337.stock1337.Model.Admin;
import stock1337.stock1337.Model.Person;
import stock1337.stock1337.Model.User;
import stock1337.stock1337.Repository.AdminRepository;
import stock1337.stock1337.Repository.PersonRepository;
import stock1337.stock1337.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final PersonRepository personRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;


    public AuthenticationResponse registePerson(RegisterRequest request) {
        var user = Person.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var admin = personRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
         return AuthenticationResponse.builder()
                 .token(jwtToken).user(admin)
                 .build();
    }


    public AuthenticationResponse registerUser(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf("USER"));

        System.out.println(user);

        var user1 = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
         return AuthenticationResponse.builder()
                 .token(jwtToken).user(user1)
                 .build();

    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.valueOf("ADMIN"));

        System.out.println(admin);
        var Admin = adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken).user(Admin)
                .build();

    }

    public AuthenticationResponse Authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = personRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }
}
