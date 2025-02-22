package dio.dio.spring.security.jwt.controller;

import dio.dio.spring.security.jwt.dtos.Login;
import dio.dio.spring.security.jwt.dtos.Sessao;
import dio.dio.spring.security.jwt.model.User;
import dio.dio.spring.security.jwt.repository.UserRepository;
import dio.dio.spring.security.jwt.security.JWTCreator;
import dio.dio.spring.security.jwt.security.JWTObject;
import dio.dio.spring.security.jwt.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login) {
        // Verificando se o usuário existe no banco
        User user = repository.findByUsername(login.getUsername());
        if (user != null) {
            // Verificando se a senha está correta
            boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }

            // Criando o objeto Sessão com as informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            System.out.println("Chave secreta: " + securityConfig.KEY);

            // Criando o objeto JWTObject para gerar o token
            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(user.getUsername());  // Definindo o sujeito como o nome do usuário
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));  // Data de emissão do token
            jwtObject.setExpiration(new Date(System.currentTimeMillis() + securityConfig.EXPIRATION));  // Usando a expiração configurada
            jwtObject.setRoles(user.getRoles());  // Definindo as roles do usuário

            // Criando o token JWT com o JWTObject
            String token = JWTCreator.create(securityConfig.PREFIX, securityConfig.KEY, jwtObject);
            sessao.setToken(token);

            return sessao;  // Retorna o objeto Sessao com o token JWT
        } else {
            throw new RuntimeException("Erro ao tentar fazer login: Usuário não encontrado");
        }
    }
}
