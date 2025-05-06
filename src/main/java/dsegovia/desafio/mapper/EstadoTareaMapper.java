package dsegovia.desafio.mapper;

import dsegovia.desafio.dto.EstadoTareaDTO;
import dsegovia.desafio.entity.EstadoTarea;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstadoTareaMapper {
    EstadoTareaDTO toDto(EstadoTarea estadoTarea);
    List<EstadoTareaDTO> toDtoList(List<EstadoTarea> estadoTareas);
}