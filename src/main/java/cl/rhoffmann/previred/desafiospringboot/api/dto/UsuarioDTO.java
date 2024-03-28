package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * UsuarioDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class UsuarioDTO {

  private Long id;

  private String nombre;

  private String correo;

  public UsuarioDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UsuarioDTO(Long id, String nombre, String correo) {
    this.id = id;
    this.nombre = nombre;
    this.correo = correo;
  }

  public UsuarioDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UsuarioDTO nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  @NotNull 
  @Schema(name = "nombre", example = "Malto Anselmo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public UsuarioDTO correo(String correo) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsuarioDTO usuarioDTO = (UsuarioDTO) o;
    return Objects.equals(this.id, usuarioDTO.id) &&
        Objects.equals(this.nombre, usuarioDTO.nombre) &&
        Objects.equals(this.correo, usuarioDTO.correo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, correo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
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

