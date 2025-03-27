package com.tecnova.nuevospa.mapper.common;

import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * Defines the way in which objects should be converted between model - DTO -
 * model.<br>
 * The Implementations must have @{@link Mapper}<br>
 * 
 * @param <M> related model.
 * @param <R> related Dto Request.
 * @param <RP> related Dto Response.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface BaseMapper<M, R, RP> {

	/**
	 * Mapper a Model object to its respective DTO.<br>
	 * 
	 * @param entity [{@link M}], This is the Model.<br>
	 * @return {@link RP}, DTO Response corresponding to the Entity.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	RP toDto(M entity);

	/**
	 * Mapper a DTO object to its respective Model.<br>
	 * 
	 * @param dto [{@link R}], This is the DTO Request.<br>
	 * @return {@link M}, Model corresponding to the DTO.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	M toModel(R dto);

	/**
	 * Mapper an List Models to its respective List Dtos with its Basic data.<br>
	 * 
	 * @param list [{@link List} of type {@link M}], This is the List Models.<br>
	 * @return {@link List} of type {@link RP}, List of corresponding Dtos Response.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public default List<RP> toDto(List<M> list) {
		return CollectionUtils.isEmpty(list) ? List.of(): list.stream().map(this::toDto).toList();
	}

}
