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
 * Entity that manages the information about the users.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@SuperBuilder
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @Column(name = "username", nullable = false, length = 150)
	private String username;
	
	@Basic(optional = false)
    @Column(name = "password" , nullable = false, length = 150)
	private String password;

	@Basic(optional = true)
    @Column(name = "token_access" , nullable = true, length = 300)
	private String tokenAccess;
}
