# 📌 API Segura com Spring Security e JWT

Este projeto implementa segurança em uma API Spring Boot utilizando Spring Security e JWT para autenticação e autorização de usuários.

## 🔒 Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Banco de Dados H2 (ou outro configurado)
- Maven

## 🚀 Funcionalidades Implementadas

### ✅ Habilitação da Segurança na API

A segurança foi ativada na aplicação através do **Spring Security**, protegendo os endpoints contra acessos não autorizados.

### 🔧 Configuração Manual da Segurança

A configuração de segurança foi feita manualmente, definindo regras de acesso aos endpoints e permitindo autenticação de usuários.

### 🔑 Consulta de Usuários no Banco de Dados

Os usuários são armazenados e consultados a partir de um banco de dados, garantindo um sistema de autenticação dinâmico e escalável.

### 🔐 Implementação de JWT para Segurança

A autenticação foi aprimorada utilizando **JWT (JSON Web Token)**, permitindo:

- Geração de tokens após login
- Validação automática de tokens em requisições protegidas
- Implementação de filtros para garantir que apenas usuários autenticados possam acessar os recursos protegidos

## 📜 Como Executar o Projeto

1. **Clone o repositório:**

   ```sh
   git clone https://github.com/Dvd-coder-art/DIO-REST-API.git
   cd seu-repositorio
   ```

2. **Configure as dependências:**
   Certifique-se de que o arquivo `pom.xml` contém as dependências corretas para Spring Security e JWT.

3. **Execute a aplicação:**

   ```sh
   mvn spring-boot:run
   ```

4. **Testando a API:**

   - Para autenticar um usuário, envie um `POST` para `/auth/login` com credenciais válidas.
   - Utilize o token retornado para acessar endpoints protegidos.

## 📌 Endpoints Principais

- `POST /auth/login` → Realiza a autenticação e retorna um token JWT.
- `GET /usuarios` → Retorna a lista de usuários (necessita autenticação).
- `POST /usuarios` → Cadastra um novo usuário.

## 📚 Referências

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [JWT Official Site](https://jwt.io/)
- [DIO](https://web.dio.me/course)

📌 **Desenvolvido por David Souza** 🚀



