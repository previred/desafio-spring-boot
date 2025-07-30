package com.app.model;

import java.net.URI;
import java.util.Objects;
import com.app.model.EstadoTareaRef;
import com.app.model.UsuarioRef;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Tarea
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Tarea {

  private Long id;

  private String nombre;

  private UsuarioRef usuario;

  private EstadoTareaRef estadoTarea;

  public Tarea() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Tarea(String nombre, UsuarioRef usuario, EstadoTareaRef estadoTarea) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.estadoTarea = estadoTarea;
  }

  public Tarea id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Tarea nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  @NotNull 
  @Schema(name = "nombre", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Tarea usuario(UsuarioRef usuario) {
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
  public UsuarioRef getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioRef usuario) {
    this.usuario = usuario;
  }

  public Tarea estadoTarea(EstadoTareaRef estadoTarea) {
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
  public EstadoTareaRef getEstadoTarea() {
    return estadoTarea;
  }

  public void setEstadoTarea(EstadoTareaRef estadoTarea) {
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
    Tarea tarea = (Tarea) o;
    return Objects.equals(this.id, tarea.id) &&
        Objects.equals(this.nombre, tarea.nombre) &&
        Objects.equals(this.usuario, tarea.usuario) &&
        Objects.equals(this.estadoTarea, tarea.estadoTarea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, usuario, estadoTarea);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tarea {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
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

