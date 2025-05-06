package dsegovia.desafio.service.impl;

import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.repository.EstadoTareaRepository;
import dsegovia.desafio.service.EstadoTareaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoTareaServiceImpl implements EstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;

    public EstadoTareaServiceImpl(EstadoTareaRepository estadoTareaRepository) {
        this.estadoTareaRepository = estadoTareaRepository;
    }

    @Override
    public List<EstadoTarea> listarTodos() {
        return estadoTareaRepository.findAll();
    }
}