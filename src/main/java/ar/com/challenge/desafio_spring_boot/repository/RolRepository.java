package ar.com.challenge.desafio_spring_boot.repository;

import ar.com.challenge.desafio_spring_boot.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByName(String name);
}