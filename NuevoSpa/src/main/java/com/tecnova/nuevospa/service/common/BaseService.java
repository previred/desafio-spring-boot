package com.tecnova.nuevospa.service.common;

import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnova.nuevospa.entity.common.BaseEntity;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.GeneralServiceException;
import com.tecnova.nuevospa.exception.NoDataFoundException;
import com.tecnova.nuevospa.exception.ValidateServiceException;
import com.tecnova.nuevospa.mapper.common.BaseMapper;
import com.tecnova.nuevospa.repository.common.BaseRepository;

import jakarta.transaction.Transactional;

/**
 * Base that define base to be implemented in the services when this contains a basic
 * transactions.<br>
 * @param <D> related DTO Response.
 * @param <DR> related DTO Request.
 * @param <E> related Entity.
 * @param <M> related Mapper.
 * @param <R> related Repository.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */

public abstract class BaseService<D, DR, E extends BaseEntity, M extends BaseMapper<E, DR, D>, R extends JpaRepository<E, Long> & BaseRepository<E>>  implements TemplateService<D, DR>{
	
	@Autowired
    protected R repository;
	
	@Autowired
    protected M mapper;
	

	/**
	 * Create with data related.<br>
	 * 
	 * @param dto {@link DR}, This is the data to create.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public D create(DR dto){
		return this.saveData(dto);
	}

	/**
	 * Retrieve all data.<br>
	 * 
	 * @return {@link D} of type {@link List}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public List<D> getAll() {
		return mapper.toDto(repository.findAllByDeletedIsFalse());
	}

	/**
	 * Retrieve data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public D getById(Long id) {
		if(ObjectUtils.isEmpty(id)) {
			throw new ValidateServiceException(MessageEnum.ID_REQUIRED);
		}
		var data =repository.findById(id).orElse(null);
		return mapper.toDto(data);
	}

	/**
	 * Retrieve data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public D getByCode(String code) {
		if(ObjectUtils.isEmpty(code)) {
			throw new ValidateServiceException(MessageEnum.CODE_REQUIRED);
		}
		var data =repository.findByCode(code).orElseThrow(()->new NoDataFoundException(MessageEnum.DATA_NO_FOUND));
		return mapper.toDto(data);
	}

	/**
	 * Update with data related.<br>
	 * 
	 * @param dto {@link DR}, This is the data to update.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public D update(DR dto) {
		return this.saveData(dto);
	}
	
	@Transactional
	private D saveData(DR dto) {
		try {
			E entity = mapper.toModel(dto);
			E entitySaved = repository.save(entity);
			return mapper.toDto(entitySaved);
		}catch (Exception e) {
			throw new GeneralServiceException(MessageEnum.ERROR_PROCESS, e);
		}
	}

	
	/**
	 * Delete data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		if(ObjectUtils.isEmpty(id)) {
			throw new ValidateServiceException(MessageEnum.ID_REQUIRED);
		}
		repository.deleteById(id);
		
	}

	/**
	 * Delete data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	@Transactional
	public void delete(String code) {
		if(ObjectUtils.isEmpty(code)) {
			throw new ValidateServiceException(MessageEnum.ID_REQUIRED);
		}
		var data =repository.findByCode(code).orElseThrow(()->new NoDataFoundException(MessageEnum.DATA_NO_FOUND));
		repository.delete(data);
		
	}
	
	
	
	
	

}
