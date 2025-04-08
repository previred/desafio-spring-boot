package cl.previred.desafio.taskmanager.infrastructure.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtConfigProperties {
    private String secretKey;
    private Long expiration;
}
