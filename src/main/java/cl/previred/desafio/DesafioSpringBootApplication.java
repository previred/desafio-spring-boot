package cl.previred.desafio;

import cl.previred.desafio.entity.UsuarioEntity;
import cl.previred.desafio.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DesafioSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSpringBootApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			if (usuarioRepository.findByUsername("admin_previred").isEmpty()) {
				UsuarioEntity admin = UsuarioEntity.builder()
						.username("admin_previred")
						.password(passwordEncoder.encode("admin123"))
						.build();
				usuarioRepository.save(admin);
				System.out.println(">>> Usuario creado exitosamente con BCrypt");
			}
		};
	}
}


