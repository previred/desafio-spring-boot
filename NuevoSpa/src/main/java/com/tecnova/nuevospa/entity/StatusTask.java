package com.tecnova.nuevospa.entity;

import com.tecnova.nuevospa.entity.common.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Entity that manages the information about the status tasks.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Entity
@Table(name = "estados_tarea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@SuperBuilder
public class StatusTask extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
	private String name;

}
