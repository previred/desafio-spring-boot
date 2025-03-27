package com.tecnova.nuevospa.config;

import java.util.TimeZone;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.tecnova.nuevospa.constant.ConstantsApp;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Class in charge of containing the system configurations.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Configuration
public class AppConfig {

	/**
	 * Method in charge of initializing and configuring the Bean
	 * {@link PasswordEncoder}, in charge of encrypting the application's passwords.
	 * 
	 * @return PasswordEncoder type {@link PasswordEncoder}: Bean initialized.
	 * @author GCR.</br>
	 *         Developer:Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Method in charge of initializing and configuring the Bean
	 * {@link StringEncryptor}, responsible for encryption of sensitive data in
	 * .properties or other uses.
	 * 
	 * @return StringEncryptor type {@link StringEncryptor}: Bean initialized.
	 * @author GCR.</br>
	 *          Developer:Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Bean(name = "encryptorBean")
	static StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("GcrNuevoSpa12345*");//can be used as an environment variable
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
	}
	
	
	 /**
    * Method in charge of initializing and configuring the Bean {@link MessageSource}, in charge of internationalizing the messages.
    * @return MessageSource type {@link MessageSource}: Bean initialized.  
	 * @author GCR.</br>
	 *          Developer:Giovanny Camacho[giovannycr004@gmail.com]
    */
	@Bean
   MessageSource messageSource() {
       ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
       	messageSource.setBasename(ConstantsApp.PATH_LANGUAJE);
       	messageSource.setDefaultEncoding(ConstantsApp.ENCODING_UTF8);
           messageSource.setCacheSeconds(1);
       return messageSource;
   }
	
	
	 /**
     * Method in charge of initializing and configuring the Bean {@link OpenAPI}, in charge of the ApiRest documentation of the application [OpenApi(Swagger)].
     * @return OpenAPI type {@link OpenAPI}: Bean initialized.  
	 * @author GCR.</br>
	 *          Developer:Giovanny Camacho[giovannycr004@gmail.com]
     */
   @Bean
    OpenAPI customOpenAPI() {    	
        return new OpenAPI()
        		      .components(new Components()
        		        .addSecuritySchemes(ConstantsApp.AUTH_METHOD_BEARER, new SecurityScheme()
        		          .name(ConstantsApp.AUTH_METHOD_BEARER)
        		          .type(SecurityScheme.Type.HTTP)
        		          .scheme(ConstantsApp.AUTH_METHOD_BEARER)
        		          .bearerFormat("JWT"))
        		        .addSecuritySchemes(ConstantsApp.AUTH_METHOD_BASIC, new SecurityScheme()
              		          .name(ConstantsApp.AUTH_METHOD_BASIC)
            		          .type(SecurityScheme.Type.HTTP)
            		          .scheme(ConstantsApp.AUTH_METHOD_BASIC))        		       
        		    		  ) 
                .info(new Info()
                        .title("Nuevo Spa[Tecnova]")
                        .version("1.0.0")
                        .description("Nuevo Spa rest api documentation developed by Giovanny Camacho[giovannycr004@gmail.com]"));
    }
   
   
	 /**
    * Method in charge of initializing and configuring the Bean {@link Jackson2ObjectMapperBuilder}, in charge of formatting the dates of the json response in UTC.
    * @return Jackson2ObjectMapperBuilder  {@link Jackson2ObjectMapperBuilder}: Bean initialized.  
	 * @author GCR.</br>
	 *          Developer:Giovanny Camacho[giovannycr004@gmail.com]
    */
	@Bean
	Jackson2ObjectMapperBuilder jacksonBuilder() {
	    Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
	    b.timeZone(TimeZone.getTimeZone(ConstantsApp.TIMEZONE_UTC));
	    b.dateFormat(new StdDateFormat().withColonInTimeZone(true));
	    return b;
	}

}
