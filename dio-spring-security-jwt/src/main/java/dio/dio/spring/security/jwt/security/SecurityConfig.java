package dio.dio.spring.security.jwt.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

    public long EXPIRATION;
    public static String PREFIX;
    public  static String KEY;




    // Métodos Getter para acessar as variáveis
    public String getPrefix() {
        return PREFIX;
    }

    public String getKey() {
        return KEY;
    }

    public Long getExpiration() {
        return EXPIRATION;
    }

    // Métodos Setter para que o Spring defina os valores automaticamente
    public void setPrefix(String prefix) {
        this.PREFIX= prefix;
    }

    public void setKey(String key) {
        this.KEY= key;
    }

    public void setExpiration(Long expiration) {
        this.EXPIRATION = expiration;
    }
}
