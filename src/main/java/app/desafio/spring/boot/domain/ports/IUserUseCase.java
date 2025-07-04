package app.desafio.spring.boot.domain.ports;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import app.desafio.spring.boot.domain.model.User;

import java.util.Optional;

public interface IUserUseCase {

    User createUser(UserDTO user);
    Optional<User> getUserByUsername(String username);

}
