package com.nuevospa.taskmanager.service;

import com.nuevospa.taskmanager.dto.EstadoTareaResponse;
import com.nuevospa.taskmanager.entity.EstadoTarea;
import com.nuevospa.taskmanager.repository.EstadoTareaRepository;
import com.nuevospa.taskmanager.util.NuevoSPAParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoTareaServiceImpl implements EstadoTareaService {

	private EstadoTareaRepository estadoTareaRepository;
	
    public EstadoTareaServiceImpl(EstadoTareaRepository estadoTareaRepository) {
        this.estadoTareaRepository = estadoTareaRepository;
    }

//	@Override
//	public Iterable<EstadoTareaResponse> obtenerTodosEstados() {
//		Iterable<EstadoTarea> estados = estadoTareaRepository.findAll();
//		return convertToEstadoTareaResponse(estados);
//	}

//	@Override
//	public Optional<EstadoTareaResponse> obtenerEstadoPorId(Long id) {
//		Optional<EstadoTarea> estadoOpt = estadoTareaRepository.findById(id);
//		return estadoOpt.map(EstadoTareaResponse::new);
//	}
//
//	@Override
//	public EstadoTareaResponse crearEstadoTarea(EstadoTarea estadoTarea) {
//		EstadoTarea estadoGuardado = estadoTareaRepository.save(estadoTarea);
//		return new EstadoTareaResponse(estadoGuardado);
//	}
//
//	@Override
//	public EstadoTareaResponse actualizarEstadoTarea(EstadoTarea estadoTarea) {
//		if (estadoTareaRepository.existsById(estadoTarea.getId())) {
//			EstadoTarea estadoActualizado = estadoTareaRepository.save(estadoTarea);
//			return new EstadoTareaResponse(estadoActualizado);
//		}
//		return null;
//	}
//
//	@Override
//	public void eliminarEstadoTarea(Long id) {
//		estadoTareaRepository.deleteById(id);
//	}
//
//	private Iterable<EstadoTareaResponse> convertToEstadoTareaResponse(Iterable<EstadoTarea> estados) {
//		return () -> estados.iterator().next(); 
//	}
}