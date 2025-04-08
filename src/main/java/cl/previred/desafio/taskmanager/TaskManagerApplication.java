package cl.previred.desafio.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
		"cl.previred.desafio.taskmanager.domain",
		"cl.previred.desafio.taskmanager.application",
		"cl.previred.desafio.taskmanager.infrastructure"
})
@EntityScan(basePackages = "cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities")
@EnableJpaRepositories(basePackages = "cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories")
@EnableConfigurationProperties
@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
