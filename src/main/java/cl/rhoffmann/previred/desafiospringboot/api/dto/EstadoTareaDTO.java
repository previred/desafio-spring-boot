package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * EstadoTareaDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class EstadoTareaDTO {

  private Long id;

  private String nombre;

  public EstadoTareaDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public EstadoTareaDTO(Long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public EstadoTareaDTO id(Long id) {
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

  public EstadoTareaDTO nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  @NotNull 
  @Schema(name = "nombre", example = "EN_PROCESO", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstadoTareaDTO estadoTareaDTO = (EstadoTareaDTO) o;
    return Objects.equals(this.id, estadoTareaDTO.id) &&
        Objects.equals(this.nombre, estadoTareaDTO.nombre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadoTareaDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
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

