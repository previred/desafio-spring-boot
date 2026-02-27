package cl.previred.taskmanager.infrastructure.persistence.adapter;

import cl.previred.taskmanager.domain.model.User;
import cl.previred.taskmanager.domain.repository.UserRepository;
import cl.previred.taskmanager.infrastructure.persistence.mapper.UserMapper;
import cl.previred.taskmanager.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserMapper::toDomain);
    }
}
