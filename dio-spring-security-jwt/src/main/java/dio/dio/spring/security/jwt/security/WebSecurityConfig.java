package dio.dio.spring.security.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Bean para criptografar senhas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // URLs do Swagger que devem ser permitidas sem autenticação
    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    // Configuração de segurança HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF, pois estamos usando JWT
                .cors(cors -> {}) // Habilita CORS
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Permite o acesso ao H2 Console
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Usando JWT (Stateless)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_WHITELIST).permitAll() // Permite o acesso às URLs do Swagger sem autenticação
                        .requestMatchers("/h2-console/*").permitAll() // Permite o acesso ao H2 Console
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // Permite POST no login sem autenticação
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // Permite POST para criar usuários
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS") // Requer autorização para acessar GET /users
                        .requestMatchers("/managers").hasAnyRole("MANAGERS") // Requer autorização para acessar /managers
                        .anyRequest().authenticated() // Requer autenticação para qualquer outra requisição
                )
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro JWT após a autenticação padrão
                .build();
    }
}
