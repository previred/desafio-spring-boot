package com.tecnova.nuevospa.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Class in charge of containing the system constants.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public class ConstantsApp {

	public static final String API_APP = "/api/nuevospa";
	public static final String PATH_LANGUAJE = "classpath:lang/messages";
	public static final String UNDEFINED = "undefined";
	public static final String IP_CLIENT = "IP_CLIENT";
	public static final String TRACE_CODE = "TRACE_CODE";
	public static final String USER = "USER";
	public static final String END_POINT = "EndPoint: ";
	public static final String AUTHORIZATION = "Authorization";
	public static final String ACCEPT_LENGUAJE = "Accept-Language";

	public static final String HEADER_AUTHORIZATION = AUTHORIZATION;
	public static final String HEADER_ORIGIN = "Origin";
	public static final String HEADER_ACCEPT_LENGUAJE = ACCEPT_LENGUAJE;
	public static final String HEADER_TRACE_CODE = "Trace-Code";

	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=" + ENCODING_UTF8;
	public static final String TIMEZONE_UTC = "UTC";
	public static final String LOCALE_DEFAULT = "es";
	public static final String LOCALE_DEFAULT_CO = LOCALE_DEFAULT + "_CO";
	public static final String LOCALE_DEFAULT_CO_HEADER = LOCALE_DEFAULT + "-CO";
	public static final String SWAGGER_PATH = "swagger-ui";
	public static final String SWAGGER_PATH_V3 = "v3/api-docs";
	public static final String SWAGGER_SCHEMA_STRING = "string";
	public static final String SWAGGER_HEADER_TRACE_CODE_DESCRIP = "Code which identifies the trace of the operation.";
	public static final String SWAGGER_HEADER_TRACE_CODE_EXAMPLE = "G5140-C044041-R0444";
	public static final String AUTH_METHOD_BEARER = "bearer";
	public static final String AUTH_METHOD_BASIC = "basic";
	public static final String TOKEN_TYPE = "token_type";
	public static final String TOKEN_ACCESS = "token_access";
	public static final String ROLE_USER = "USER";

	public static final List<String> PUBLIC_ROUTES_SWAGGER = List.of(API_APP + "/" + SWAGGER_PATH,
			API_APP + "/" + SWAGGER_PATH_V3);

	public static final String MASK_PASSWORD = "*******";
	public static final String GENERAL_ACCESS_PATH = "/**";
	public static final String AUTH_ROUTE = API_APP + "/auth";
	public static final List<String> ENDPOINTS_PERMITALL = List.of("/auth");

	public static final List<String> PUBLIC_ROUTES = List.of(AUTH_ROUTE);
	public static final String ALLOWED_HEADERS_STRING = "HEAD,GET,POST,PUT,DELETE,PATCH,OPTIONS";
	public static final List<String> ACCESS_SWAGGER = List.of("/" + SWAGGER_PATH_V3 + "/**", "/swagger-resources/**",
			"/swagger-resources", "/" + SWAGGER_PATH + "/**", "/" + SWAGGER_PATH + ".html");
	public static final List<String> ROLES_SWAGGER = List.of("SWAGGER", ROLE_USER);
	public static final List<String> ALLOWED_METHODS = List.of(ALLOWED_HEADERS_STRING.split(","));
	public static final List<String> BASIC_HEADERS = List.of(HEADER_ACCEPT_LENGUAJE, HEADER_ORIGIN, HEADER_TRACE_CODE,
			"Content-Type");
	public static final List<String> AUTH_HEADERS = List.of(HEADER_AUTHORIZATION);
	private static final List<String> FULL_HEADERS;
	public static final String FULL_HEADERS_STRING;

	public static final String MSG_OPENAPI_GENERIC_CONTROLLER = "<strong>You must be previously authenticated using the authentication api</strong>.";
	public static final String MSG_OPENAPI_GENERIC_CONTROLLER_METHODS = "<strong>You must first authenticate using the authentication api, obtain the token and click on the 'Authorize' button or the padlock icon and enter it in the generated dialog.</strong></br>";

	static {
		FULL_HEADERS = new ArrayList<>(BASIC_HEADERS);
		getFullHeaders().addAll(AUTH_HEADERS);
		FULL_HEADERS_STRING = String.join(",", FULL_HEADERS);
	}

	/**
	 * Method in charge of obtaining the header allowed in the system.
	 * 
	 * @return List {@link List}<{@link String}>: Header obtained.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public static List<String> getFullHeaders() {
		return FULL_HEADERS;
	}
}
