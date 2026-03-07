package com.previred.desafiostareas.controller.dto;


import com.fasterxml.jackson.annotation.JsonValue;
import com.previred.desafiostareas.validation.ValidPrioridad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de tarea request
 */
@Getter
@Setter
@Builder
@Schema(description = "Objeto de tarea request")
public class TareaRequest {

    @NotNull
    @Schema(description = "Título de la tarea", example = " comprar")
    private String titulo;

    @NotNull
    @Schema(description = "Descripción de la tarea", example = "Comprar Respuestos para el auto")
    private String descripcion;


    @Schema(description = "Nivel de prioridad de la tarea", example = "1")
    @ValidPrioridad
    private TareaRequest.PrioridadEnum prioridad;

    @Schema(description = "ID del estado de la tarea", example = "1")
    private Long estadoId;


    /**
     * Nivel de prioridad de la tarea
     */
    public enum PrioridadEnum {
        BAJA(1, "BAJA"),
        MEDIA(2, "MEDIA"),
        ALTA(3, "ALTA");

        @Getter
        private final Integer id;
        private final String value;

        PrioridadEnum(Integer id, String value) {
            this.id = id;
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * Estado de la tarea
     */
    public enum EstadoEnum {
        PENDIENTE(1, "PENDIENTE"),
        EN_PROGRESO(2, "EN_PROGRESO"),
        COMPLETADA(3, "COMPLETADA"),
        CANCELADA(4, "CANCELADA");

        @Getter
        private final Integer id;
        private final String value;

        EstadoEnum(Integer id, String value) {
            this.id = id;
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

}
