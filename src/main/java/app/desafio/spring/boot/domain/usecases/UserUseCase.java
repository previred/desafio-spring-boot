package app.desafio.spring.boot.domain.usecases;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import app.desafio.spring.boot.domain.model.User;
import app.desafio.spring.boot.domain.ports.IUserUseCase;
import app.desafio.spring.boot.infrastructure.persistence.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
final class UserUseCase implements IUserUseCase {


    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserUseCase(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserDTO user) {
        if (user.username() == null || user.username().isEmpty() ||
                user.password() == null || user.password().isEmpty()) {
            throw new IllegalArgumentException("The fields username, password, and role cannot be empty.");
        }

        if (userRepository.findByUsername(user.username()).isPresent()) {
            throw new IllegalArgumentException("User with username '" + user.username() + "' already exists.");
        }

        String encodedPassword = passwordEncoder.encode(user.password());

        User userModel = new User();
        userModel.setUsername(user.username());
        userModel.setPassword(encodedPassword);
        userModel.setRole("USER");
        return userRepository.save(userModel);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<User> users = userRepository.findByUsername(username);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        return users;
    }
}
