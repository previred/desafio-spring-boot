package com.previred.desafiostareas.service;

import com.previred.desafiostareas.controller.dto.TareaRequest;
import com.previred.desafiostareas.controller.dto.TareaResponse;
import java.util.List;

public interface TareaService {

    List<TareaResponse> obtenerTodasLasTareas();
    TareaResponse obtenerTareaPorId(Long id);
    TareaResponse crearTarea(String bearerToken,TareaRequest request);
    TareaResponse actualizarTarea(Long id, String bearerToken, TareaRequest request);
    void eliminarTarea(Long id);


}
