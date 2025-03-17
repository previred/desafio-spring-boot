package com.cifuentes.cristobal.gestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cifuentes.cristobal.gestion.model.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
	
	 Optional<Status> findByName(String name);

}
