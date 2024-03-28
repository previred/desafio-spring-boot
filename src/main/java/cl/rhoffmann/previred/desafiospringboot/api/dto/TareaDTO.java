package cl.rhoffmann.previred.desafiospringboot.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * TareaDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T14:04:30.140044600-03:00[America/Santiago]", comments = "Generator version: 7.4.0")
public class TareaDTO {

  private Long id;

  private String nombre;

  private String descripcion;

  private EstadoTareaDTO estadoTarea;

  private UsuarioDTO usuario;

  public TareaDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TareaDTO(Long id, String nombre, EstadoTareaDTO estadoTarea, UsuarioDTO usuario) {
    this.id = id;
    this.nombre = nombre;
    this.estadoTarea = estadoTarea;
    this.usuario = usuario;
  }

  public TareaDTO id(Long id) {
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

  public TareaDTO nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  @NotNull 
  @Schema(name = "nombre", example = "Tarea de ejemplo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public TareaDTO descripcion(String descripcion) {
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

  public TareaDTO estadoTarea(EstadoTareaDTO estadoTarea) {
    this.estadoTarea = estadoTarea;
    return this;
  }

  /**
   * Get estadoTarea
   * @return estadoTarea
  */
  @NotNull @Valid 
  @Schema(name = "estadoTarea", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("estadoTarea")
  public EstadoTareaDTO getEstadoTarea() {
    return estadoTarea;
  }

  public void setEstadoTarea(EstadoTareaDTO estadoTarea) {
    this.estadoTarea = estadoTarea;
  }

  public TareaDTO usuario(UsuarioDTO usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  */
  @NotNull @Valid 
  @Schema(name = "usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("usuario")
  public UsuarioDTO getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioDTO usuario) {
    this.usuario = usuario;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TareaDTO tareaDTO = (TareaDTO) o;
    return Objects.equals(this.id, tareaDTO.id) &&
        Objects.equals(this.nombre, tareaDTO.nombre) &&
        Objects.equals(this.descripcion, tareaDTO.descripcion) &&
        Objects.equals(this.estadoTarea, tareaDTO.estadoTarea) &&
        Objects.equals(this.usuario, tareaDTO.usuario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, descripcion, estadoTarea, usuario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TareaDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    estadoTarea: ").append(toIndentedString(estadoTarea)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
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

