package dio.dio.spring.security.jwt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {

    @Value("${security.config.key}")
    private String secretKey;

    public String getSecretKey() {
        System.out.println("Secret Key: " + secretKey);  // Apenas para teste
        return secretKey;
    }
}
