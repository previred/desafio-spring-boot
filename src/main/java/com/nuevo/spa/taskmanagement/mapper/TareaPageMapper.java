package com.nuevo.spa.taskmanagement.mapper;

import com.nuevo.spa.taskmanagement.generated.model.GetAllTareas200Response;
import com.nuevo.spa.taskmanagement.model.Tarea;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class TareaPageMapper {

    public static GetAllTareas200Response toGetAllTareas200Response(Page<Tarea> tareasPage) {
        GetAllTareas200Response response = new GetAllTareas200Response();
        response.setContent(
                tareasPage.getContent().stream()
                        .map(TareaMapper::toDTO)
                        .collect(Collectors.toList())
        );
        response.setTotalPages(tareasPage.getTotalPages());
        response.setTotalElements((int) tareasPage.getTotalElements());

        return response;
    }
}
