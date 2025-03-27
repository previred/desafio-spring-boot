package com.tecnova.nuevospa.security;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.AuthenticationsException;
import com.tecnova.nuevospa.security.service.SecurityService;

import lombok.RequiredArgsConstructor;
/**
 * AUTH provider class, in charge of performing HTTP_BASIC authentication.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
@RequiredArgsConstructor
public class AppAuthenticationProvider implements AuthenticationProvider {
	
	private final SecurityService securityService;
	
	/**
	 * Method in charge of performing HTTP_BASIC authentication.
	 * 
	 * @param authentication {@link Authentication}: Authentication to be performed.
	 * @return Authentication {@link Authentication}: result.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationsException {
			if ((authentication.getName() == null || authentication.getName().isEmpty())
					&& (authentication.getCredentials().toString() == null
							|| authentication.getCredentials().toString().isEmpty())) {
				throw new AuthenticationsException(MessageEnum.CREDENCIALS_REQUIRED);
			}
			if (authentication.getName() == null || authentication.getName().isEmpty()) {
				throw new AuthenticationsException(MessageEnum.USER_REQUIRED);
			}
			if (authentication.getCredentials().toString() == null
					|| authentication.getCredentials().toString().isEmpty()) {
				throw new AuthenticationsException(MessageEnum.PASSWORD_REQUIRED);
			}

			AuthResponseDto userAuth=securityService.authenticate(
					authentication.getName(),
					authentication.getCredentials().toString());
			List<SimpleGrantedAuthority> grantedAuths = List.of(
				    new SimpleGrantedAuthority("ROLE_USER"), 
				    new SimpleGrantedAuthority("ROLE_SWAGGER")
				);
			return new UsernamePasswordAuthenticationToken(userAuth,ConstantsApp.MASK_PASSWORD, grantedAuths);
	}
	
	/**
     * Configuration method in charge of assigning the allowed authentication methods.
     * @param authentication{@link Class}: Authentication to config.
     * @return boolean: result to config.  
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
     * @throws
     */
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
