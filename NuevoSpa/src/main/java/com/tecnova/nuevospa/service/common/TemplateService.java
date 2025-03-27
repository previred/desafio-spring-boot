package com.tecnova.nuevospa.service.common;

import java.util.List;

/**
 * Template that define base to be implemented in the services when this contains a basic
 * transactions.<br>
 * @param <D> related DTO Response.
 * @param <R> related DTO Request.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface TemplateService<D, R> {
	
	/**
	 * Create with data related.<br>
	 * 
	 * @param dto {@link R}, This is the data to create.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    D create(R dto);
    
	/**
	 * Retrieve all data.<br>
	 *
	 * @return {@link D} of type {@link List}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    List<D> getAll();
    
	/**
	 * Retrieve data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    D getById(Long id);
    
	/**
	 * Retrieve data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    D getByCode(String code);
    
	/**
	 * Update with data related.<br>
	 * 
	 * @param dto {@link R}, This is the data to update.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    D update(R dto);
    
	/**
	 * Delete data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    void delete(Long id);
    
	/**
	 * Delete data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
    void delete(String code);
	

}
