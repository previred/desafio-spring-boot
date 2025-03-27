package com.tecnova.nuevospa.util;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.controller.common.WrapperResponse;
import com.tecnova.nuevospa.enums.CategoryMessageEnum;
import com.tecnova.nuevospa.enums.ClassMessageEnum;
import com.tecnova.nuevospa.enums.MessageEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
/**
 * Utility class in charge of managing the response's answers.
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service
@Slf4j
public class ResponseUtil {
	
	@Autowired
	private WebUtil webUtil;
	private ObjectMapper objectMapper = new ObjectMapper();

	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param body  {@link T}: Body a asignar al response.
     * @param isOk  {@link boolean}: valor a asignar al response para determinar si la petición se realizó correctamente.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
     *  @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private <T> WrapperResponse<T> buildResponseWrapper(MessageEnum message,Object[] paramMessage,T body,boolean isOk,Exception e,HttpServletRequest request){
		String lang=ConstantsApp.LOCALE_DEFAULT;
		if(!ObjectUtils.isEmpty(request)) {
			lang=this.webUtil.getLang(request);
		}	
		String msg=this.webUtil.translate(message,paramMessage,lang);	
		String trace=this.webUtil.getTraceCode(request);
		if(message.equals(MessageEnum.OK)) {
			log.info(msg,(!ObjectUtils.isEmpty(e)?e:null));
		}else {
			log.error(msg,(!ObjectUtils.isEmpty(e)?e:null));	
		}
		return new  WrapperResponse<>(isOk, msg, message.getCode(), message.getCategory().name(), message.getClas().name(), trace, body);
	}
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link ErrorHandlerConfig}.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param body  {@link T}: Body a asignar al response.
     * @param isOk  {@link boolean}: valor a asignar al response para determinar si la petición se realizó correctamente.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	@SuppressWarnings("unchecked")
	private <T> T buildResponseWrapperObject(MessageEnum message,Object[] paramMessage,T body,boolean isOk,Exception e,WebRequest request){
		String lang=ConstantsApp.LOCALE_DEFAULT;
		if(!ObjectUtils.isEmpty(request)) {
			lang=this.webUtil.getLang(request);
		}		
		String msg=this.webUtil.translate(message,paramMessage,lang);		
		String trace=this.webUtil.getTraceCode(request);
		log.error(msg,(!ObjectUtils.isEmpty(e)?e:null));
		return (T) new  WrapperResponse<>(isOk, msg, message.getCode(), message.getCategory().name(), message.getClas().name(),trace, body);
	}
	
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link AuthenticationEntryPointConfig}  o Filters.
     * @param response  {@link HttpServletResponse}: Response a tratar.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @param statusCode  {@link Integer}: statusCode a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param ex  {@link Exception}: Error presentado a asignar al response.
     *
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private void buildErrorResponse(HttpServletResponse response,HttpServletRequest request,Integer statusCode, MessageEnum message,Object[] paramMessage, Exception ex) {
		 response.setContentType(ConstantsApp.CONTENT_TYPE_JSON_UTF8);
	     response.setStatus(statusCode);
	     response.setCharacterEncoding(ConstantsApp.ENCODING_UTF8);
	     response.setHeader("Access-Control-Allow-Origin","*"); 
	     response.setHeader("Access-Control-Allow-Methods",ConstantsApp.ALLOWED_HEADERS_STRING);
	     response.setHeader("Access-Control-Allow-Headers", ConstantsApp.FULL_HEADERS_STRING);
	     response.setHeader("Access-Control-Allow-Credentials", "true");

	     if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	         response.setStatus(HttpServletResponse.SC_OK);
	         return;
	     }
	     
	     try {
	    	 OutputStream out = response.getOutputStream();
	         PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
	         writer.println(objectMapper.writeValueAsString(this.buildErrorResponseWrapper(message,ex,request,paramMessage)));
		} catch (Exception e) {
			String lang=ConstantsApp.LOCALE_DEFAULT;
			if(!ObjectUtils.isEmpty(request)) {
				lang=this.webUtil.getLang(request);
			}			
			log.error(this.webUtil.translate(MessageEnum.ERROR_PROCESS,lang), e);
		} 
	}
	 /**
     * Método encargado de construir el {@link ResponseEntity} a patir de {@link ErrorHandlerConfig}.
     * @param status  {@link HttpStatus}: Status a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param body  {@link T}: Body a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return ResponseEntity  {@link ResponseEntity}: Response construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private <T> ResponseEntity<T> buildErrorResponseGeneric(HttpStatus status, Exception e,MessageEnum message,T body, Object[] paramMessage,WebRequest request) {	
		return ResponseEntity.status(status).body(this.buildErrorResponseWrapperObject(message,e,body,paramMessage,request));
	}
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link ErrorHandlerConfig}.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param body  {@link T}: Body a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private <T> T buildErrorResponseWrapperObject(MessageEnum message,Exception e,T body, Object[] paramMessage,WebRequest request){
		return this.buildResponseWrapperObject(message, paramMessage, body, false,e,request);
	}	

	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link AuthenticationEntryPointConfig} o Filters.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	private <T> WrapperResponse<T> buildErrorResponseWrapper(MessageEnum message,Exception e, HttpServletRequest request,Object[] paramMessage){
		return this.buildResponseWrapper(message, paramMessage, null, false,e,request);
	}	

	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response.
     * @param body  {@link T}: Body a asignar al response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(T body){
		return this.buildResponseWrapper(MessageEnum.OK, null, body, true,null,this.webUtil.getRequest());
	}
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response sin body.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(){
		return this.buildResponseWrapper(MessageEnum.OK, null, null, true,null,this.webUtil.getRequest());
	}
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param body  {@link T}: Body a asignar al response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
    * @author </br>
     * Developer: Arnold Campillo
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(MessageEnum message, T body){
		if(message.getCategory()!=CategoryMessageEnum.INFO||message.getClas()!=ClassMessageEnum.GENERIC) {
			message=MessageEnum.OK;
		}
		return this.buildResponseWrapper(message, null, body, true,null,this.webUtil.getRequest());
	}
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param body  {@link T}: Body a asignar al response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(MessageEnum message,Object[] paramMessage, T body){
		if(message.getCategory()!=CategoryMessageEnum.INFO||message.getClas()!=ClassMessageEnum.GENERIC) {
			message=MessageEnum.OK;
		}
		return this.buildResponseWrapper(message, paramMessage, body, true,null,this.webUtil.getRequest());
	}
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response sin body.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(MessageEnum message){
		if(message.getCategory()!=CategoryMessageEnum.INFO||message.getClas()!=ClassMessageEnum.GENERIC) {
			message=MessageEnum.OK;
		}
		return this.buildResponseWrapper(message, null, null, true,null,this.webUtil.getRequest());
	}
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta exitosa del response sin body.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @return WrapperResponse  {@link WrapperResponse}: Wrapper construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> WrapperResponse<T> buildSuccessResponseWrapper(MessageEnum message,Object[] paramMessage){
		if(message.getCategory()!=CategoryMessageEnum.INFO||message.getClas()!=ClassMessageEnum.GENERIC) {
			message=MessageEnum.OK;
		}
		return this.buildResponseWrapper(message, paramMessage, null, true,null,this.webUtil.getRequest());
	}
	 /**
     * Método encargado de construir el {@link ResponseEntity} a patir de {@link ErrorHandlerConfig}.
     * @param status  {@link HttpStatus}: Status a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @return ResponseEntity  {@link ResponseEntity}: Response construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	
	public <T> ResponseEntity<T> buildErrorResponse(HttpStatus status,MessageEnum message) {
		return this.buildErrorResponseGeneric(status,null,message,null,null,null);
	}
	 /**
     * Método encargado de construir el {@link ResponseEntity} a patir de {@link ErrorHandlerConfig}.
     * @param status  {@link HttpStatus}: Status a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param body  {@link T}: Body a asignar al response.   
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return ResponseEntity  {@link ResponseEntity}: Response construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> ResponseEntity<T> buildErrorResponse(HttpStatus status, Exception e,MessageEnum message,Object[] paramMessage,T body,WebRequest request) {
		return this.buildErrorResponseGeneric(status,e,message,body,paramMessage,request);
	}
	 /**
     * Método encargado de construir el {@link ResponseEntity} a patir de {@link ErrorHandlerConfig}.
     * @param status  {@link HttpStatus}: Status a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return ResponseEntity  {@link ResponseEntity}: Response construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> ResponseEntity<T> buildErrorResponse(HttpStatus status, Exception e,MessageEnum message,Object[] paramMessage,WebRequest reques) {
		return this.buildErrorResponseGeneric(status,e,message,null,paramMessage,reques);
	}
	 /**
     * Método encargado de construir el {@link ResponseEntity} a patir de {@link ErrorHandlerConfig}.
     * @param status  {@link HttpStatus}: Status a asignar al response.
     * @param e  {@link Exception}: Error presentado a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param request  {@link WebRequest}: Request para tratar y obtener información relevante para el response.
     * @return ResponseEntity  {@link ResponseEntity}: Response construido.  
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public <T> ResponseEntity<T> buildErrorResponse(HttpStatus status, Exception e,MessageEnum message,WebRequest request) {
		return this.buildErrorResponseGeneric(status,e,message,null,null,request);
	}
	
	
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link AuthenticationEntryPointConfig}  o Filters.
     * @param response  {@link HttpServletResponse}: Response a tratar.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @param statusCode  {@link Integer}: statusCode a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     *
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public void buildErrorResponse(HttpServletResponse response,HttpServletRequest request,Integer statusCode, MessageEnum message,Object[] paramMessage) {
		 this.buildErrorResponse(response, request,statusCode,message, paramMessage,null );
	}
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link AuthenticationEntryPointConfig}  o Filters.
     * @param response  {@link HttpServletResponse}: Response a tratar.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @param statusCode  {@link Integer}: statusCode a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param ex  {@link Exception}: Error presentado a asignar al response.
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public void buildErrorResponse(HttpServletResponse response,HttpServletRequest request,Integer statusCode, MessageEnum message, Exception ex) {		
		 this.buildErrorResponse(response,request, statusCode,message, null ,ex);
	}
	 /**
     * Método encargado de construir el {@link WrapperResponse} para la respuesta del response a patir de {@link AuthenticationEntryPointConfig}  o Filters.
     * @param response  {@link HttpServletResponse}: Response a tratar.
     * @param request  {@link HttpServletRequest}: Request para tratar y obtener información relevante para el response.
     * @param statusCode  {@link Integer}: statusCode a asignar al response.
     * @param message  {@link MessageEnum}: Mensaje a asignar al response.
     * @param ex  {@link Exception}: Error presentado a asignar al response.
     * @param paramMessage  {@link Object}[]: Parametros requeridos para el mensaje.
     * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */
	public void buildErrorResponse(HttpServletResponse response,HttpServletRequest request,Integer statusCode, MessageEnum message, Exception ex,Object[] paramMessage) {		
		 this.buildErrorResponse(response,request, statusCode,message, paramMessage ,ex);
	}
	
}
