package com.tecnova.nuevospa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * {@link Enum} contains system messages.<br>
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Getter
@AllArgsConstructor
public enum MessageEnum {
	
	OK("app.message.000","APP_000", CategoryMessageEnum.INFO, ClassMessageEnum.GENERIC),
	ERROR("app.message.999","APP_999", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	ERROR_PROCESS("app.message.998","APP_998", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	METHOD_NOT_ALLOWED("app.message.900","APP_900", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST("app.message.901","APP_901", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_QUERY_PARAM("app.message.902","APP_902", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_PATH_PARAM("app.message.903","APP_903", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_TYPE_DATA("app.message.904","APP_904", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_TYPE_DATA_BODY("app.message.905","APP_905", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_TYPE_DATA_GENERIC("app.message.906","APP_906", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	BAD_REQUEST_VALID_PARAMS("app.message.907","APP_907", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	ERROR_AUTH("app.message.908","APP_908", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	AUTH_REQUIRED("app.message.909","APP_909", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	HEADERS_REQUIRED("app.message.910","APP_910", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	HEADERS_STRUCTURE_INVALID("app.message.911","APP_911", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	CORS_ORIGIN_ERROR("app.message.912","APP_912", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	HEADER_REQUIRED("app.message.913","APP_913", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	HEADER_EQUALS("app.message.914","APP_914", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	LANG_NO_SOPORT("app.message.915","APP_915", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	RE_AUTH_REQUIRED("app.message.916","APP_916", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	NOT_FOUND_RESOURCE("app.message.917","APP_917", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	CREDENCIALS_REQUIRED("app.message.001","APP_001", CategoryMessageEnum.ERROR,ClassMessageEnum.BUSINESS_ERROR),
	USER_REQUIRED("app.message.002","APP_002", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	PASSWORD_REQUIRED("app.message.003","APP_003", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	TOKEN_INCORRECT("app.message.004","APP_004", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_EXPIRED("app.message.005","APP_005", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_REFRESH_INCORRECT("app.message.006","APP_006", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_REFRESH_EXPIRED("app.message.007","APP_007", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_INCORRECT_USER("app.message.008","APP_008", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_INCORRECT_USER_NO_AUTH("app.message.009","APP_009", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_REFRESH_ATTEMPTS("app.message.010","APP_010", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	TOKEN_REFRESH_NO_CORRECT("app.message.011","APP_011", CategoryMessageEnum.ERROR, ClassMessageEnum.TECHNICAL_ERROR),
	USER_NO_FOUND("app.message.012","APP_012", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_NO_ACTIVE("app.message.013","APP_013", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_BLOQ_PASS_INCORRECT("app.message.014","APP_014", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_PASS_INCORRECT("app.message.015","APP_015", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_FOUND("app.message.016","APP_016", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_NO_FOUND_ID("app.message.017","APP_017", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USER_CHANGE_PASS("app.message.018","APP_018", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	USERCODE_NO_FOUND("app.message.019","APP_019", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	ID_REQUIRED("app.message.020","APP_020", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	DATA_NO_FOUND("app.message.021","APP_021", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	CODE_REQUIRED("app.message.022","APP_022", CategoryMessageEnum.ERROR, ClassMessageEnum.BUSINESS_ERROR),
	;
	
	private String message;
	private String code;
	private CategoryMessageEnum category;
	private ClassMessageEnum clas;
	

}

