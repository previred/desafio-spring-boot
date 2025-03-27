package com.tecnova.nuevospa.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tecnova.nuevospa.entity.common.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * Entity that manages the information about the tasks.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Entity
@Table(name = "tareas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@SuperBuilder
public class Task extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@Basic(optional = true)
    @Column(name = "description", nullable = true, length = 150)
	private String description;
	
	@Basic(optional = false)
	@Column(name = "start_task", nullable = false, columnDefinition = "DATETIME")
	@Builder.Default
	private LocalDateTime startTask = LocalDateTime.now();
	
	@Basic(optional = true)
	@Column(name = "end_task", nullable = true, columnDefinition = "DATETIME")
	@Builder.Default
	private LocalDateTime endTask = LocalDateTime.now();
	
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private User user;
	
	
	@ManyToOne(targetEntity = StatusTask.class, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "id_status_task", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private StatusTask statusTask;

}
