package gestion.tareas.demo.infrastructure.configuration.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JsonErrorResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonErrorResponseUtil() {
    }

    public static void sendError(HttpServletResponse response, int status, String mensaje,String loggedUser) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("loggedUser", loggedUser);
        body.put("message", mensaje);

        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }
}
