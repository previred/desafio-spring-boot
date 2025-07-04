package app.desafio.spring.boot.infrastructure.util;

import app.desafio.spring.boot.domain.model.TaskStatus;
import app.desafio.spring.boot.domain.model.User;
import app.desafio.spring.boot.infrastructure.persistence.ITaskRepository;
import app.desafio.spring.boot.infrastructure.persistence.ITaskStatusRepository;
import app.desafio.spring.boot.infrastructure.persistence.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderUtil implements CommandLineRunner {

    private final IUserRepository IUserRepository;
    private final ITaskStatusRepository ITaskStatusRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoaderUtil(IUserRepository IUserRepository, ITaskRepository ITaskRepository, ITaskStatusRepository ITaskStatusRepository, PasswordEncoder passwordEncoder) {
        this.IUserRepository = IUserRepository;
        this.ITaskStatusRepository = ITaskStatusRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (IUserRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            String encodedPassword = passwordEncoder.encode("admin");
            user.setPassword(encodedPassword);
            user.setRole("ADMIN");
            IUserRepository.save(user);
        }

        if (IUserRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            String encodedPassword = passwordEncoder.encode("user");
            user.setPassword(encodedPassword);
            user.setRole("USER");
            IUserRepository.save(user);
        }

        if (ITaskStatusRepository.count() == 0) {
            ITaskStatusRepository.save(new TaskStatus("PENDING"));
            ITaskStatusRepository.save(new TaskStatus("IN_PROGRESS"));
            ITaskStatusRepository.save(new TaskStatus("COMPLETED"));
            ITaskStatusRepository.save(new TaskStatus("CANCELLED"));
        }
    }
}
