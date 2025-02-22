package dio.dio.spring.security.jwt.controller;

import dio.dio.spring.security.jwt.model.User;
import dio.dio.spring.security.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<String> postUser(@RequestBody User user) {
        // Verifica se o usuário já existe antes de criar um novo
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("O nome de usuário é obrigatório.");
        }

        // Criação do usuário
        try {
            service.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o usuário: " + e.getMessage());
        }
    }
}
