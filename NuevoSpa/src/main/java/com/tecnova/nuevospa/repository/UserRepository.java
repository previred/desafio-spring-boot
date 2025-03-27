package com.tecnova.nuevospa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.repository.common.BaseRepository;
/**
 * Repository that manages the information about the user {@link User}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Repository
public interface UserRepository extends BaseRepository<User>, JpaRepository<User, Long>{
	
	/**
	 * Find data with the username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Find data with the token.<br>
	 * 
	 * @param token {@link String}, This is the token for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	Optional<User> findByTokenAccess(String tokenAccess);

}