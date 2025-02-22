package dio.dio.spring.security.jwt.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

public class JWTCreator {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    private JWTConfig jwtConfig;

    public JWTCreator(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    public static String create(String prefix, String secretKey, JWTObject jwtObject) {
        // Obtendo a chave secreta da classe de configuração


        // Gerando o JWT com a chave secreta
        return Jwts.builder()
                .setSubject(jwtObject.getSubject())  // Usando o nome do usuário como subject
                .setIssuedAt(jwtObject.getIssuedAt())  // Data de emissão
                .setExpiration(jwtObject.getExpiration())  // Data de expiração
                .claim("roles", jwtObject.getRoles())  // Adicionando roles como claim
                .signWith(SignatureAlgorithm.HS512, secretKey)  // Assinando com a chave secreta
                .compact();  // Gerando o token JWT
    }

    // Método de verificação dos papéis (roles) para garantir que comecem com "ROLE_"
    private static List<String> checkRoles(List<String> roles) {
        return roles.stream()
                .map(s -> s.startsWith("ROLE_") ? s : "ROLE_" + s)
                .collect(Collectors.toList());
    }

    // Método de criação de JWTObject a partir do token (para decodificar)
    public static JWTObject create(String token, String prefix, String key) {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "").trim();  // Remove o prefixo "Bearer"

        // Validando e decodificando o token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)  // Faz a validação e decodificação do token JWT
                .getBody();

        // Preenchendo o JWTObject com as informações extraídas
        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());

        // Lidar com os papéis (roles)
        Object roles = claims.get(ROLES_AUTHORITIES);
        if (roles instanceof List) {
            object.setRoles((List<String>) roles);
        } else if (roles instanceof String) {
            object.setRoles(List.of(((String) roles).split(",")));
        }

        return object;
    }
}
