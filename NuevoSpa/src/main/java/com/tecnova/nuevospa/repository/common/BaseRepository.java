package com.tecnova.nuevospa.repository.common;

import java.util.List;
import java.util.Optional;
import com.tecnova.nuevospa.entity.common.BaseEntity;

/**
 * Defines base to be implemented in the repositories when this contains a basic
 * queries.<br>
 * 
 * @param <M> related model.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface BaseRepository<M extends BaseEntity> {

	/**
	 * Find data with the code.<br>
	 * 
	 * @param code {@link String}, This is the code for the query.<br>
	 * @return {@link Optional} of type {@link M}, data obtained.<br>
	 * 	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	Optional<M> findByCode(String code);
	
	
	/**
	 * Find all data which are not deleted.<br>
	 * 
	 * @return {@link List} of type {@link M}, data obtained.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	List<M> findAllByDeletedIsFalse();

	
	/**
	 * Find all data which are deleted.<br>
	 * 
	 * @return {@link List} of type {@link M}, data obtained.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	List<M> findAllByDeletedIsTrue();

}
