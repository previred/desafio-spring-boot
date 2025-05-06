package dsegovia.desafio.mapper;

import dsegovia.desafio.dto.TareaRequest;
import dsegovia.desafio.dto.TareaResponse;
import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.entity.Tarea;
import dsegovia.desafio.entity.Usuario;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades Tarea y DTOs
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TareaMapper {

    /**
     * Convierte un DTO TareaRequest a una entidad Tarea
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(source = "estadoId", target = "estado", qualifiedByName = "estadoIdToEstado")
    @Mapping(target = "etiquetas", expression = "java(convertirEtiquetasAString(tareaRequest.getEtiquetas()))")
    Tarea toEntity(TareaRequest tareaRequest);

    /**
     * Convierte una entidad Tarea a un DTO TareaResponse
     */
    @Mapping(source = "estado.id", target = "estadoId")
    @Mapping(source = "estado.nombre", target = "estadoNombre")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.username", target = "ownerUsername")
    @Mapping(source = "etiquetas", target = "etiquetas", qualifiedByName = "etiquetasToList")
    TareaResponse toDto(Tarea tarea);

    /**
     * Actualiza una entidad Tarea con datos de un DTO TareaRequest
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(source = "estadoId", target = "estado", qualifiedByName = "estadoIdToEstado")
    @Mapping(target = "etiquetas", expression = "java(convertirEtiquetasAString(tareaRequest.getEtiquetas()))")
    void updateEntityFromDto(TareaRequest tareaRequest, @MappingTarget Tarea tarea);

    /**
     * Convierte una lista de etiquetas a string separado por comas
     */
    default String convertirEtiquetasAString(List<String> etiquetas) {
        if (etiquetas == null || etiquetas.isEmpty()) {
            return null;
        }
        return String.join(",", etiquetas);
    }

    /**
     * Convierte un string de etiquetas separadas por comas a una lista
     */
    @Named("etiquetasToList")
    default List<String> etiquetasToList(String etiquetas) {
        if (etiquetas == null || etiquetas.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(etiquetas.split(","));
    }

    /**
     * Convierte un ID de estado a un objeto EstadoTarea
     */
    @Named("estadoIdToEstado")
    default EstadoTarea estadoIdToEstado(Long estadoId) {
        if (estadoId == null) {
            return null;
        }
        EstadoTarea estado = new EstadoTarea();
        estado.setId(estadoId);
        return estado;
    }

    /**
     * Mapea usuario a DTO simplificado
     */
    Usuario mapUsuario(Usuario usuario);

    /**
     * Mapea estado a DTO
     */
    EstadoTarea mapEstado(EstadoTarea estadoTarea);
}