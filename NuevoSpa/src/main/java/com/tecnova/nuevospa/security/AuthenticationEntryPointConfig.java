package com.tecnova.nuevospa.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.AuthenticationsException;
import com.tecnova.nuevospa.util.ResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Configuration class in charge of customizing the errors presented when
 * performing HTTP_BASIC authentication.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointConfig extends BasicAuthenticationEntryPoint {

	private final ResponseUtil responseUtil;

	/**
	 * Method in charge of error handling({@link AuthenticationException}) and its
	 * respective customization.
	 * 
	 * @param request        {@link HttpServletRequest}: Request for use of error
	 *                       handlingr.
	 * @param response{@link HttpServletResponse}: response for use of error
	 *                       handling.
	 * @param authException  {@link AuthenticationException}: Error generated.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws AuthenticationsException {
		String requestURI = request.getRequestURI();
		if (isPublicRouteSwagger(requestURI)) {
			response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
		}

		this.responseUtil.buildErrorResponse(response, request, HttpServletResponse.SC_UNAUTHORIZED,
				MessageEnum.AUTH_REQUIRED, authException);

	}

	/**
	 * Configuration method in charge of assigning the realmName.
	 * 
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	@Override
	public void afterPropertiesSet() {
		setRealmName("tecnova-nuevospa");
		super.afterPropertiesSet();
	}

	/**
	 * Method in charge of validating if the request is from swagger(OpenAPi).
	 * 
	 * @param requestURI {@link String}: Request Url to validate.
	 * @return {@link boolean}: validation result.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private boolean isPublicRouteSwagger(String requestURI) {
		return ConstantsApp.PUBLIC_ROUTES_SWAGGER.stream().anyMatch(requestURI::contains);
	}

}
