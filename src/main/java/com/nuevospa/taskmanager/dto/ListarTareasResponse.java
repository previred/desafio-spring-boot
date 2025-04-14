package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.dto.TareaDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListarTareasResponse extends RespuestaBase{
    private List<TareaDTO> listaTareas;
}
