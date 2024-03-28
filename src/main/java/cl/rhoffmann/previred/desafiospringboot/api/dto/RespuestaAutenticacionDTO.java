package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * RespuestaAutenticacionDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class RespuestaAutenticacionDTO {

  private String correo;

  private String tokenAcceso;

  public RespuestaAutenticacionDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RespuestaAutenticacionDTO(String correo, String tokenAcceso) {
    this.correo = correo;
    this.tokenAcceso = tokenAcceso;
  }

  public RespuestaAutenticacionDTO correo(String correo) {
    this.correo = correo;
    return this;
  }

  /**
   * Get correo
   * @return correo
  */
  @NotNull @javax.validation.constraints.Email 
  @Schema(name = "correo", example = "admin@admin.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("correo")
  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public RespuestaAutenticacionDTO tokenAcceso(String tokenAcceso) {
    this.tokenAcceso = tokenAcceso;
    return this;
  }

  /**
   * Get tokenAcceso
   * @return tokenAcceso
  */
  @NotNull 
  @Schema(name = "tokenAcceso", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGFkbWluIiwiaXNzIjoiUHJldmlyZWQiLCJpYXQiOjE3MTE1NTAxMjksImV4cCI6MTcxMTYzNjUyOX0.cvx5od9y1xzc5FYEgk4Y4t-T85U9QXbYhuGGpvYUlL8EZtcltY9JjyEl6N6CIIT-UC0VpUebVhi12NaQ2Ub0GQ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tokenAcceso")
  public String getTokenAcceso() {
    return tokenAcceso;
  }

  public void setTokenAcceso(String tokenAcceso) {
    this.tokenAcceso = tokenAcceso;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RespuestaAutenticacionDTO respuestaAutenticacionDTO = (RespuestaAutenticacionDTO) o;
    return Objects.equals(this.correo, respuestaAutenticacionDTO.correo) &&
        Objects.equals(this.tokenAcceso, respuestaAutenticacionDTO.tokenAcceso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correo, tokenAcceso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RespuestaAutenticacionDTO {\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    tokenAcceso: ").append(toIndentedString(tokenAcceso)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

