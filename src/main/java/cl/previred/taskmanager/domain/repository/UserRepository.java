package cl.previred.taskmanager.domain.repository;

import cl.previred.taskmanager.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

}
