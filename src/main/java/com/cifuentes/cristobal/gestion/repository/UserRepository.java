package com.cifuentes.cristobal.gestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cifuentes.cristobal.gestion.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByEmail(String email);
}
