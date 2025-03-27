package com.tecnova.nuevospa.entity.common;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class Base that manages the information common about the entities.
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@Column(name = "code", nullable = false, length = 50)
	@Builder.Default
	private String code = UUID.randomUUID().toString();
	
	@Basic(optional = false)
	@Column(name = "creation_date", nullable = false, columnDefinition = "DATETIME")
	@Builder.Default
	private LocalDateTime creationDate = LocalDateTime.now();

	@Basic(optional = false)
	@Column(name = "deleted", length = 1, columnDefinition = "boolean default false")
	@Builder.Default
	private boolean deleted = false;



}

