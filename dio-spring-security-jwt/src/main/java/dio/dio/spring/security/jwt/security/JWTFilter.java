package dio.dio.spring.security.jwt.security;

import java.io.IOException;
import java.util.List;
import java.util.Base64;
import java.util.stream.Collectors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtém o token do cabeçalho Authorization
        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        try {
            if (token != null && !token.isEmpty()) {
                // Remove o prefixo "Bearer"
                token = token.replace("Bearer", "").trim();

                // Decodifica a parte do payload do token
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payload = parts[1];
                    byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
                    String decodedJwt = new String(decodedBytes);
                    System.out.println("Decoded JWT Payload: " + decodedJwt);  // Exibe o conteúdo do payload
                } else {
                    System.out.println("Token JWT inválido");
                }

                // Decodifica o token e valida as informações
                JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);

                // Converte os roles para authorities
                List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

                // Cria o token de autenticação e armazena no contexto de segurança
                UsernamePasswordAuthenticationToken userToken =
                        new UsernamePasswordAuthenticationToken(
                                tokenObject.getSubject(),
                                null,
                                authorities);

                SecurityContextHolder.getContext().setAuthentication(userToken);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    // Converte os roles para SimpleGrantedAuthority
    private List<SimpleGrantedAuthority> authorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
