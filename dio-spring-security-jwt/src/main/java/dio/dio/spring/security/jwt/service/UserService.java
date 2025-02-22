package dio.dio.spring.security.jwt.service;

import dio.dio.spring.security.jwt.model.User;
import dio.dio.spring.security.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    // Método para criar um usuário
    public void createUser(User user) {
        // Criptografando a senha antes de salvar no banco de dados
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Define a senha criptografada no usuário

        // Salvando o usuário no repositório
        repository.save(user);
    }

    // Método adicional para verificar se o usuário existe (opcional, dependendo do seu fluxo)
    public boolean userExists(String username) {
        return repository.existsByUsername(username);
    }
}
