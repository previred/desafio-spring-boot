package cl.previred.desafio.infrastructure.security.filters.utils;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtProperties {
    private static String TOKEN_KEY="JSDJLKJLSAIOUO88587SAD87AS6D89ASD980798SAD3HJKHP9SDICKSDICKNSD";
    public static SecretKey JWT_KEY= Keys.hmacShaKeyFor(TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
    public static String PREFIX="Bearer ";
}
