package com.tecnova.nuevospa.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.AuthenticationsException;
import com.tecnova.nuevospa.exception.ValidateServiceException;
import com.tecnova.nuevospa.security.service.JwtServices;
import com.tecnova.nuevospa.security.service.SecurityService;
import com.tecnova.nuevospa.util.ResponseUtil;
import com.tecnova.nuevospa.util.WebUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * Security class which is a request filter in charge of validating the security conditions in the requests.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class Securityfilter extends OncePerRequestFilter {

	private final JwtServices jwtServices;
	private final SecurityService securityService;
	private final WebUtil webUtil;
	private final ResponseUtil responseUtil;

	 /**
    * Method responsible for detecting and validating the security conditions of the request.
    * 
    * @param request {@link HttpServletRequest}: Request to validate.
    * @param response {@link HttpServletResponse}: Response to validate.
    * @param filterChain{@link FilterChain}: filterChain intecerted.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
    * @throws
    */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, ValidateServiceException, AuthenticationsException {
		try {
			
			String requestURI = request.getRequestURI();		
	        if (webUtil.isPublicRoute(requestURI) || webUtil.isPublicRouteSwagger(requestURI) || !webUtil.hasAuthorizationBearer(request)) {
	            filterChain.doFilter(request, response);
	            return;
	        }

			String token = webUtil.getToken(request);
			AuthResponseDto userToken=jwtServices.getUserFromToken(token);
			securityService.validateTokenUser(userToken.username(),token);
			if (StringUtils.hasText(token) && !jwtServices.validateAccessToken(token)) {				
				log.info(ConstantsApp.END_POINT+ requestURI);
				filterChain.doFilter(request, response);
				return;
			}
			
			securityService.setAuthenticationContext(token, request);			
			log.info(ConstantsApp.END_POINT+ requestURI);

		}catch (Exception e) {
			if(e instanceof ValidateServiceException ex) {
				this.responseUtil.buildErrorResponse(response,request, HttpServletResponse.SC_UNAUTHORIZED,ex.getError(),ex,ex.getParamMessage());
			}else if(e instanceof AuthenticationsException ex) {
				this.responseUtil.buildErrorResponse(response,request, HttpServletResponse.SC_UNAUTHORIZED,ex.getError(),ex,ex.getParamMessage());
			}else{
				this.responseUtil.buildErrorResponse(response,request, HttpServletResponse.SC_UNAUTHORIZED, MessageEnum.ERROR,e);			    
			}	
			return;
		}
	    response.setCharacterEncoding(ConstantsApp.ENCODING_UTF8);
        response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setHeader("Access-Control-Allow-Methods",ConstantsApp.ALLOWED_HEADERS_STRING);
        response.setHeader("Access-Control-Allow-Headers", ConstantsApp.FULL_HEADERS_STRING);
        response.setHeader("Access-Control-Allow-Credentials", "true");
		filterChain.doFilter(request, response);
	}

}
