package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * TareaPutDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class TareaPutDTO {

  private String nombre;

  private String descripcion;

  private EstadoTareaRefDTO estadoTarea;

  public TareaPutDTO nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  
  @Schema(name = "nombre", example = "Tarea de ejemplo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public TareaPutDTO descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * Get descripcion
   * @return descripcion
  */
  
  @Schema(name = "descripcion", example = "Esto es solo un ejemplo de descripción de la tarea para mostrar un ejemplo del contenido.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("descripcion")
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public TareaPutDTO estadoTarea(EstadoTareaRefDTO estadoTarea) {
    this.estadoTarea = estadoTarea;
    return this;
  }

  /**
   * Get estadoTarea
   * @return estadoTarea
  */
  @Valid 
  @Schema(name = "estadoTarea", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("estadoTarea")
  public EstadoTareaRefDTO getEstadoTarea() {
    return estadoTarea;
  }

  public void setEstadoTarea(EstadoTareaRefDTO estadoTarea) {
    this.estadoTarea = estadoTarea;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TareaPutDTO tareaPutDTO = (TareaPutDTO) o;
    return Objects.equals(this.nombre, tareaPutDTO.nombre) &&
        Objects.equals(this.descripcion, tareaPutDTO.descripcion) &&
        Objects.equals(this.estadoTarea, tareaPutDTO.estadoTarea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre, descripcion, estadoTarea);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TareaPutDTO {\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    estadoTarea: ").append(toIndentedString(estadoTarea)).append("\n");
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

