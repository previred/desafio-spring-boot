package com.tecnova.nuevospa.security.service.impl;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.ValidateServiceException;
import com.tecnova.nuevospa.security.service.JwtServices;

/**
 * Service in charge of performing JWT transactions and operations.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service
public class JwtServicesImpl implements JwtServices {
	
	
	@Value("${app.jwt.expiration.token}")
	private int jwtTokenExpiration;
	
	@Value("${app.jwt.secret}")
	private String jwtSecret;


	/**
	 * Method responsible for creating access tokens.
	 * 
	 * @param user {@link AuthResponseDto}: User to assign the token.
	 * @return user {@link AuthResponseDto}: User Configured with tokens.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public AuthResponseDto createToken(AuthResponseDto user) {
		return generateToken(user);
	}

	/**
	 * Method in charge of obtaining the user of the token.
	 * 
	 * @param token {@link String}: access token to be validated.
	 * @return result @link AuthResponseDto}:user obtained from token
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public AuthResponseDto getUserFromToken(String token) throws ValidateServiceException {
		try {
			  Claims claims = Jwts.parser()
			            .setSigningKey(jwtSecret)
			            .parseClaimsJws(token)
			            .getBody();
			  
			 Object userObj = claims.get("user");
			 ObjectMapper objectMapper = new ObjectMapper();
		    return objectMapper.convertValue(userObj, AuthResponseDto.class);

		}catch (ExpiredJwtException e) {
			throw new ValidateServiceException(MessageEnum.TOKEN_EXPIRED);
		} catch (Exception e) {
			throw new ValidateServiceException(MessageEnum.TOKEN_INCORRECT);
		}
	}

	/**
	 * Method responsible for creating access tokens.
	 * 
	 * @param user {@link AuthResponseDto}: User to assign the token.
	 * @return user {@link AuthResponseDto}: User Configured with tokens.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private AuthResponseDto generateToken(AuthResponseDto user) {
		Date now = new Date();
		Date accessTokenExpiration = DateUtils.addMinutes(now, jwtTokenExpiration);

		String accessToken = Jwts.builder().setClaims(loadClaimsToken(user)).setIssuedAt(now)
				.setExpiration(accessTokenExpiration).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

	    return AuthResponseDto.builder()
	    		.username(user.username())
	    		.code(user.code())
	    	    .tokenAccess(accessToken)
	    	    .tokenExpired(accessTokenExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
	    	    .build();

	}

	/**
	 * Method in charge of assigning properties or values to the access token.
	 * 
	 * @param user {@link AuthResponseDto}: Usuario to asing token.
	 * @return claims {@link Map}<{@link String},{@link Object}> : Map with
	 *         properties to token.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private Map<String, Object> loadClaimsToken(AuthResponseDto user) {
		Map<String, Object> claims = loadClaims(user);
		return claims;
	}

	/**
	 * Method in charge of assigning properties or generic values to the token.
	 * 
	 * @param user del tipo {@link AuthResponseDto}: Usuario to asing token.
	 * @return claims del tipo {@link Map}<{@link String},{@link Object}> :Map with
	 *         properties to token.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private Map<String, Object> loadClaims(AuthResponseDto user) {
		Map<String, Object> claims = Jwts.claims().setSubject(user.username());
		claims.put("id", user.username());
		claims.put("usercode", user.code());
		claims.put("user", user);
		return claims;
	}
	
	
	 /**
	 * Method in charge of validating the access token.
	 * @param token{@link String}: token to validate
	 * @return result {@link boolean}: result of validation.   
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public boolean validateAccessToken(String token) throws ValidateServiceException{
		try {
	        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);	
	        isTokenExpired(token);	
	        return true;
		}catch (ExpiredJwtException e) {
			throw new ValidateServiceException(MessageEnum.TOKEN_EXPIRED);
		}

	}
	

	/**
	 * Method in charge of validating if the tokens have already expired.
	 * @param token{@link String}: token to validate
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	private void isTokenExpired(String token) throws ValidateServiceException{
		Date expiration = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
		if(expiration.before(new Date())) {
			throw new ValidateServiceException(MessageEnum.TOKEN_EXPIRED);
		}		
	}
	/**
	 * Method in charge of obtaining the expired date of the token.
	 * 
	 * @param token {@link String}: access token to be validated.
	 * @return result @link {@link Date}}:Expired obtained from token
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */

	@Override
	public Date getDateExpiredFromToken(String token) throws ValidateServiceException {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
	}

}
