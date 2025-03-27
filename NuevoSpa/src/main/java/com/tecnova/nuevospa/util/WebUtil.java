package com.tecnova.nuevospa.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.enums.MessageEnum;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class in charge of managing common methods or utilities of the [Web]
 * application.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service
public class WebUtil {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método encargado de obtener el request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @return request {@link HttpServletRequest}: Request obtenido de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * Método encargado de cargar los headers del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @param headers {@link Map}<{@link String},{@link String}>: Mapa con los
	 *                headers del request.
	 * @param key     {@link String}: Key a asignar al mapa.
	 * @param value   {@link String}: Valor a asignar al mapa.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	private void loadHeaderRequest(Map<String, String> headers, String key, String value) {
		headers.put(key.toUpperCase(), value);
	}

	/**
	 * Método encargado de obtener los headers del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return headers {@link Map}<{@link String},{@link String}>: Request obtenido
	 *         de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public Map<String, String> getHeadersRequest(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			this.loadHeaderRequest(headers, key, request.getHeader(key));
		}
		return headers;
	}

	/**
	 * Método encargado de obtener los headers del request de la petición
	 * {@link WebRequest}.
	 * 
	 * @param request {@link WebRequest}: Request para obtener información.
	 * @return headers {@link Map}<{@link String},{@link String}>: Request obtenido
	 *         de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public Map<String, String> getHeadersRequest(WebRequest request) {
		Map<String, String> headers = new HashMap<>();
		Iterator<String> headerNames = request.getHeaderNames();
		while (headerNames.hasNext()) {
			String key = headerNames.next();
			this.loadHeaderRequest(headers, key, request.getHeader(key));
		}
		return headers;
	}

	/**
	 * Método encargado de obtener los headers del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @return headers {@link Map}<{@link String},{@link String}>: Request obtenido
	 *         de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public Map<String, String> getHeadersRequest() {
		Map<String, String> headers = new HashMap<>();
		HttpServletRequest request = this.getRequest();
		Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			this.loadHeaderRequest(headers, key, request.getHeader(key));
		}
		return headers;
	}

	/**
	 * Método encargado de obtener el idioma[lang] del request de la petición
	 * {@link WebRequest}.
	 * 
	 * @param request {@link WebRequest}: Request para obtener información.
	 * @return lang {@link String}: Lang obtenido del Request de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getLang(WebRequest request) {
		Map<String, String> headers = this.getHeadersRequest(request);
		return this.getDataHeader(headers, ConstantsApp.HEADER_ACCEPT_LENGUAJE.toUpperCase(),
				ConstantsApp.LOCALE_DEFAULT);
	}

	/**
	 * Método encargado de obtener el idioma[lang] del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return lang {@link String}: Lang obtenido del Request de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getLang(HttpServletRequest request) {
		Map<String, String> headers = this.getHeadersRequest(request);
		return this.getDataHeader(headers, ConstantsApp.HEADER_ACCEPT_LENGUAJE.toUpperCase(),
				ConstantsApp.LOCALE_DEFAULT);
	}

	/**
	 * Método encargado de obtener el token(token, refreshToken) de autenticación
	 * del request de la petición {@link HttpServletRequest}.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return headers {@link Map}<{@link String},{@link String}>: Request obtenido
	 *         de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getToken(HttpServletRequest request) {
		String header = request.getHeader(ConstantsApp.AUTHORIZATION);
		return header.split(" ")[1].trim();
	}

	/**
	 * Método encargado de obtener el token(token, refreshToken) de autenticación
	 * del request de la petición {@link HttpServletRequest}.
	 * 
	 * @return token {@link String}: token obtenido del Request de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getToken() {
		String header = getRequest().getHeader(ConstantsApp.AUTHORIZATION);
		return header.split(" ")[1].trim();
	}

	/**
	 * Método encargado de obtener el username del usuario del contexto de
	 * autenticacion {@link SecurityContextHolder}.
	 * 
	 * @return username {@link String}: Username del usuario obtenido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getUsername() {
		AuthResponseDto obj = this.getUser();
		return ObjectUtils.isEmpty(obj) ? ConstantsApp.UNDEFINED : obj.username();
	}

	/**
	 * Método encargado de obtener el usuario del contexto de autenticacion
	 * {@link SecurityContextHolder}.
	 * 
	 * @return dto {@link AuthResponseDto}: Información del usuario obtenido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */

	
	  public AuthResponseDto getUser() {
		if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
			return null;
		}
		if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return null;
		}

		if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return null;
		}
		if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthResponseDto)) {
			return null;
		}
		return (AuthResponseDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	/**
     * Método encargado de obtener el valor del header a partir del key.
     * @param headers {@link Map}<{@link String},{@link String}>: headers obtenidos de la petición.
     * @param key {@link String}: Key a buscar en los headers obtenidos de la petición.
     * @param valueDefault {@link String}: Valor default a asignar al header si no se encuentra de los headers enviados.
     * @return header {@link String}: Header obtenido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private String getDataHeader(Map<String, String> headers, String key, String valueDefault) {
		String data = headers.get(key);
		return ObjectUtils.isEmpty(data) ? valueDefault : data;
	}
	/**
	 * Método encargado de obtener el idioma[lang] del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @return lang {@link String}: Lang obtenido del Request de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getLang() {
		Map<String, String> headers = this.getHeadersRequest();
		return this.getDataHeader(headers, ConstantsApp.HEADER_ACCEPT_LENGUAJE.toUpperCase(),
				ConstantsApp.LOCALE_DEFAULT);
	}

	/**
	 * Método encargado de obtener el tracecode del request de la petición
	 * {@link WebRequest}.
	 * 
	 * @param request {@link WebRequest}: Request para obtener información.
	 * @return traceCode {@link String}: TraceCode obtenido de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getTraceCode(WebRequest request) {
		Map<String, String> headers = this.getHeadersRequest(request);
		return this.getDataHeader(headers, ConstantsApp.HEADER_TRACE_CODE.toUpperCase(), UUID.randomUUID().toString());
	}

	/**
	 * Método encargado de obtener el tracecode del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return traceCode {@link String}: TraceCode obtenido de la petición.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getTraceCode(HttpServletRequest request) {
		Map<String, String> headers = this.getHeadersRequest(request);
		return this.getDataHeader(headers, ConstantsApp.HEADER_TRACE_CODE.toUpperCase(), UUID.randomUUID().toString());
	}

	/**
	 * Método encargado de obtener la ip del request de la petición
	 * {@link WebRequest}.
	 * 
	 * @param request {@link WebRequest}: Request para obtener información.
	 * @return ip {@link String}: Ip obtenido del request.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getIpClient(WebRequest request) {
		HttpServletRequest servletRequest = ((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class);
		return this.getIpClient(servletRequest);
	}

	/**
	 * Método encargado de obtener la ip del request de la petición
	 * {@link HttpServletRequest}.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return ip {@link String}: Ip obtenido del request.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String getIpClient(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * Método encargado de traducir un texto a partir de su
	 * mensaje{@link MessageEnum}.
	 * 
	 * @param message      {@link MessageEnum}: Mensaje a traducir.
	 * @param paramMessage {@link Object}[]: Parametros requeridos para el mensaje.
	 * @param locale       {@link String}: Locale o idioma a traducir el texto.
	 * @return traslate {@link String}: texto traducido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String translate(MessageEnum message, Object[] paramMessage, String locale) {
		return this.messageSource.getMessage(message.getMessage(), paramMessage,
				new Locale(ObjectUtils.isEmpty(locale) ? ConstantsApp.LOCALE_DEFAULT : locale));
	}

	/**
	 * Método encargado de traducir un texto a partir de su
	 * mensaje{@link MessageEnum}, con el idioma del request.
	 * 
	 * @param message      {@link MessageEnum}: Mensaje a traducir.
	 * @param paramMessage {@link Object}[]: Parametros requeridos para el mensaje.
	 * @return traslate {@link String}: texto traducido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String translate(MessageEnum message, Object[] paramMessage) {
		return this.translate(message, paramMessage, this.getLang());
	}

	/**
	 * Método encargado de traducir un texto a partir de su
	 * mensaje{@link MessageEnum}, con el idioma del request.
	 * 
	 * @param message {@link MessageEnum}: Mensaje a traducir.
	 * @return traslate {@link String}: texto traducido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String translate(MessageEnum message) {
		return this.translate(message, null, this.getLang());
	}

	/**
	 * Método encargado de traducir un texto a partir de su
	 * mensaje{@link MessageEnum}.
	 * 
	 * @param message {@link MessageEnum}: Mensaje a traducir.
	 * @param locale  {@link String}: Locale o idioma a traducir el texto.
	 * @return traslate {@link String}: texto traducido.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public String translate(MessageEnum message, String locale) {
		return this.translate(message, null, locale);
	}

	/**
	 * Método encargado de validar si la ruta del request es una ruta publica.
	 * 
	 * @param requestURI {@link String}: Request Url a validar.
	 * @return result {@link boolean}: resultado de la validación.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public boolean isPublicRoute(String requestURI) {
		return ConstantsApp.PUBLIC_ROUTES.stream().anyMatch(requestURI::contains);
	}

	/**
	 * Método encargado de validar si la ruta del request es una ruta de
	 * swagger(OpenApi).
	 * 
	 * @param requestURI {@link String}: Request Url a validar.
	 * @return result {@link boolean}: resultado de la validación.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public boolean isPublicRouteSwagger(String requestURI) {
		return ConstantsApp.PUBLIC_ROUTES_SWAGGER.stream().anyMatch(requestURI::contains);
	}

	/**
	 * Método encargado de validar si el request tiene el encabezado de
	 * autenticacion Bearer.
	 * 
	 * @param request {@link HttpServletRequest}: Request para obtener información.
	 * @return result {@link boolean}: resultado de la validación.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader(ConstantsApp.AUTHORIZATION);
		return !(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"));

	}

}
