package stock1337.stock1337.Configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/auth/register/**",
                                "/api/v1/auth/authenticate"
                        ).permitAll()

                        .requestMatchers("/api/v1/auth/Admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/User/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/v1/auth/departements/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/stocks/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/demandes/pending").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/demandes/*/approve").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/demandes/*/reject").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/auth/demandes/create").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/v1/auth/demandes/my-demandes").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/v1/auth/articles/search").authenticated()
                        .requestMatchers("/api/v1/auth/articles/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
