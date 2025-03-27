package com.tecnova.nuevospa.security.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.dto.response.UserResponseDto;
import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.AuthenticationsException;
import com.tecnova.nuevospa.exception.NoDataFoundException;
import com.tecnova.nuevospa.exception.ValidateServiceException;
import com.tecnova.nuevospa.security.service.JwtServices;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import com.tecnova.nuevospa.security.service.SecurityService;
import com.tecnova.nuevospa.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Services that manages the transactions about the authentications.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtServices jwtServices;

	@Value("${app.attemp.pass}")
	private int attempsPassLimit;

	Map<String, Integer> attempsPass = new HashMap<>();

	/**
	 * Performs user authentication.<br>
	 * 
	 * @param username {@link String}, This is the username for the
	 *                 authentication.<br>
	 * @param password {@link String}, This is the password for the
	 *                 authentication.<br>
	 * @return {@link AuthResponseDto} data obtained.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com
	 */
	@Override
	public AuthResponseDto authenticate(String username, String password) {
		try {
			User user = userService.findUserByUsername(username);
			
			//System.out.println(passwordEncoder.encode(password));
			if (passwordMatches(password, user.getPassword())) {
				attempsPass.remove(username);
				AuthResponseDto userAuth = jwtServices
						.createToken(AuthResponseDto.builder().username(username).code(user.getCode()).build());
				userService.registryToken(username, userAuth.tokenAccess());
				return userAuth;
			}

			int attempts = attempsPass.getOrDefault(username, 0) + 1;
			if (attempts <= attempsPassLimit) {
				attempsPass.put(username, attempts);
				throw new AuthenticationsException(MessageEnum.USER_PASS_INCORRECT);
			} else {
				attempsPass.remove(username);
				throw new AuthenticationsException(MessageEnum.USER_BLOQ_PASS_INCORRECT, username);
			}
		} catch (Exception e) {
			if (e instanceof NoDataFoundException nfe) {
				throw new AuthenticationsException(nfe.getError(), nfe.getParamMessage());
			}
			throw e;
		}
	}

	/**
	 * Method in charge of validating if the passwords match.
	 * 
	 * @param rawPassword     {@link String}: Password to compare.
	 * @param encodedPassword {@link String}: Password encrypt to compare.
	 * @return matches {@link boolean}: response about validation.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com
	 */
	private boolean passwordMatches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	
	/**
	 * validate token.<br>
	 * 
	 * @param username {@link String}, This is the username for the
	 *                 authentication.<br>
	 * @param token {@link String}, This is the token for the
	 *                 authentication.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public void validateTokenUser(String username, String token) {
	
		User user = userService.findUserByUsername(username);
		boolean existsToken = ObjectUtils.isNotEmpty(user.getTokenAccess());
		
		if(!existsToken) {
			throw new AuthenticationsException(MessageEnum.TOKEN_INCORRECT);	
		}
	

		if(!user.getTokenAccess().equals(token)) {
			throw new AuthenticationsException(MessageEnum.TOKEN_INCORRECT_USER);				
		}
		
	}

	 /**
	 * Method in charge of assigning the user to the authentication context {@link SecurityContextHolder}.
	 * @param token {@link String}: token equired for the request. 
	 * @param request  {@link HttpServletRequest}: Request required for the request.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public void setAuthenticationContext(String token, HttpServletRequest request) {
		AuthResponseDto userDetails = getUserByToken(token);
		if (userDetails != null) {
			setAuthenticationContext(userDetails,request);
		}
		
	}
	
	
	
	 /**
	 /* Find data with the token.<br>
	 * 
	 * @param token {@link String}, This is the token for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	private AuthResponseDto getUserByToken(String token) throws ValidateServiceException, NoDataFoundException{
		
		UserResponseDto userDetails = userService.findByToken(token);
		
		Date dateExpiredToken = jwtServices.getDateExpiredFromToken(token);
		Instant instant = dateExpiredToken.toInstant();
		 return AuthResponseDto.builder()
		    		.username(userDetails.username())
		    		.code(userDetails.code())
		    	    .tokenAccess(token)
		    	    .tokenExpired(instant.atZone(ZoneId.systemDefault()).toLocalDateTime())
		    	    .build();
	}
	
	
	
	 /**
	 * Method in charge of assigning the user to the authentication context {@link SecurityContextHolder}.
	 * @param token {@link String}: token equired for the request. 
	 * @param request  {@link HttpServletRequest}: Request required for the request.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private void setAuthenticationContext(AuthResponseDto user,HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
				null,List.of(
					    new SimpleGrantedAuthority("ROLE_USER"), 
					    new SimpleGrantedAuthority("ROLE_SWAGGER")
					));
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
