package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * PeticionAutenticacionDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class PeticionAutenticacionDTO {

  private String correo;

  private String contrasena;

  public PeticionAutenticacionDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PeticionAutenticacionDTO(String correo, String contrasena) {
    this.correo = correo;
    this.contrasena = contrasena;
  }

  public PeticionAutenticacionDTO correo(String correo) {
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

  public PeticionAutenticacionDTO contrasena(String contrasena) {
    this.contrasena = contrasena;
    return this;
  }

  /**
   * Get contrasena
   * @return contrasena
  */
  @NotNull 
  @Schema(name = "contrasena", example = "UnaContrasena123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contrasena")
  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeticionAutenticacionDTO peticionAutenticacionDTO = (PeticionAutenticacionDTO) o;
    return Objects.equals(this.correo, peticionAutenticacionDTO.correo) &&
        Objects.equals(this.contrasena, peticionAutenticacionDTO.contrasena);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correo, contrasena);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeticionAutenticacionDTO {\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    contrasena: ").append(toIndentedString(contrasena)).append("\n");
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

