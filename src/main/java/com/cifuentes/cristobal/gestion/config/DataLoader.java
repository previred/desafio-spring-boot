package com.cifuentes.cristobal.gestion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cifuentes.cristobal.gestion.model.entity.Status;
import com.cifuentes.cristobal.gestion.model.entity.User;
import com.cifuentes.cristobal.gestion.repository.StatusRepository;
import com.cifuentes.cristobal.gestion.repository.UserRepository;


@Configuration
public class DataLoader {

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			  if (userRepository.findByEmail("admin@tareas.com").isEmpty()) {
		            User admin = new User();
		            admin.setName("Administrador");
		            admin.setEmail("admin@tareas.com");
		            admin.setPassword(passwordEncoder.encode("admin123"));
		            userRepository.save(admin);
		        }

		        if (userRepository.findByEmail("maria@tareas.com").isEmpty()) {
		            User maria = new User();
		            maria.setName("Maria Lopez");
		            maria.setEmail("maria@tareas.com");
		            maria.setPassword(passwordEncoder.encode("maria123"));
		            userRepository.save(maria);
		        }

		        if (userRepository.findByEmail("juan@tareas.com").isEmpty()) {
		            User juan = new User();
		            juan.setName("Juan Perez");
		            juan.setEmail("juan@tareas.com");
		            juan.setPassword(passwordEncoder.encode("juan123"));
		            userRepository.save(juan);
		        }
		};
	}
	
	@Bean
	public CommandLineRunner loadStatuses(StatusRepository statusRepository) {
	    return args -> {
	        if (statusRepository.count() == 0) {
	            statusRepository.save(new Status("PENDING"));
	            statusRepository.save(new Status("IN_PROGRESS"));
	            statusRepository.save(new Status("COMPLETED"));
	        }
	    };
	}
}
