package ar.com.challenge.desafio_spring_boot.repository;

import ar.com.challenge.desafio_spring_boot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByName(String name);
}