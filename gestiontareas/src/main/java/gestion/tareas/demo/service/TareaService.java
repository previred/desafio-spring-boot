package gestion.tareas.demo.service;

import gestion.tareas.demo.model.*;
import gestion.tareas.demo.repository.TareaRepository;
import gestion.tareas.demo.security.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AuthService authService;

    @Autowired
    EstadoTareaService estadoTareaService;

    @Autowired
    HttpServletRequest request;


    public StateAppDto getMessage(long id, boolean ok){
        StateAppDto stateAppDto = new StateAppDto();
        String metodoHttp = request.getMethod();
        if(ok){
            if(metodoHttp.equalsIgnoreCase("POST")){
                stateAppDto.setMessage("Se registro exitosamente la tarea " + id);
            }else if(metodoHttp.equalsIgnoreCase("PUT")){
                stateAppDto.setMessage("Se actualizo exitosamente la tarea " + id);
            } else if (metodoHttp.equalsIgnoreCase("DELETE")) {
                stateAppDto.setMessage("Se elimino exitosamente la tarea " + id);
            }
        }else{
            stateAppDto.setMessage("No existe Tarea " + id);
        }

        stateAppDto.setLoggedUser(authService.obtenerUsuarioAutenticado().getUsername());
        return stateAppDto;
    }
    public ResponseEntity<StateAppDto> tareasPost(TareaRequestDto tareaRequestDto) {
        Long userId = authService.obtenerUsuarioAutenticado().getId();
        EstadoTarea estadoTarea = estadoTareaService.getEstadoTareaById(tareaRequestDto.getState().getValue());

        Tareas tareas = new Tareas();
        tareas.setDescripcion(tareaRequestDto.getDescripcion());
        tareas.setFechaCreacion(LocalDateTime.now());
        tareas.setFechaVencimiento(tareaRequestDto.getExpirationDate().toLocalDateTime());
        tareas.setUsuarioId(userId);
        tareas.setEstado(estadoTarea);

        tareaRepository.save(tareas);

        return ResponseEntity.ok(getMessage(tareas.getId(), true));
    }
    public ResponseEntity<List<TareaDto>> tareasGet(Integer usuarioId) {
        List<Tareas> tareasList = tareaRepository.findByUsuarioId(usuarioId.longValue());

        List<TareaDto> tareaDtoList = tareasList.stream().map(v-> {
            TareaDto tareaDto = new TareaDto();
            tareaDto.setId((long) v.getId().intValue());
            tareaDto.setDescripcion(v.getDescripcion());
            tareaDto.setDateCreation(v.getFechaCreacion().atOffset(ZoneOffset.UTC));
            tareaDto.setExpirationDate(v.getFechaVencimiento().atOffset(ZoneOffset.UTC));
            tareaDto.setState(v.getEstado().getNombre());
            tareaDto.setUserName(AuthUtil.obtenerUsuarioAutenticado());
            tareaDto.setUserRole(usuarioService
                    .findByUsername(AuthUtil.obtenerUsuarioAutenticado()).get().getRole());

            return tareaDto;
        }
        ).toList();

        return ResponseEntity.ok(tareaDtoList);
    }
    public ResponseEntity<StateAppDto> tareasIdPut(Long tareaId, TareaRequestDto tareaRequestDto) {
        StateAppDto stateAppDto = new StateAppDto();
        Optional<Tareas> tareaOptional = tareaRepository.findById(tareaId);
        if (tareaRepository.existsById(tareaId)) {
            Long userId = authService.obtenerUsuarioAutenticado().getId();
            EstadoTarea estadoTarea = estadoTareaService.getEstadoTareaById(tareaRequestDto.getState().getValue());

            Tareas tareas = tareaOptional.get();
            tareas.setDescripcion(tareaRequestDto.getDescripcion());
            tareas.setEstado(estadoTarea);
            tareas.setFechaVencimiento(tareaRequestDto.getExpirationDate().toLocalDateTime());
            tareas.setUsuarioId(userId);

            tareaRepository.save(tareas);

            stateAppDto = getMessage(tareaId, true);
        }else {
            stateAppDto = getMessage(tareaId, false);
        }

        return ResponseEntity.ok(stateAppDto);
    }
    public ResponseEntity<TareaDto> tareasIdGet(Long tareaId) {
        Optional<Tareas> tareasOptional = tareaRepository.findById(tareaId);
        if(tareasOptional.isPresent()){
            TareaDto tareaDto = new TareaDto();
            tareaDto.setId((long) tareasOptional.get().getId());
            tareaDto.setDescripcion(tareasOptional.get().getDescripcion());
            tareaDto.setDateCreation(tareasOptional.get().getFechaCreacion().atOffset(ZoneOffset.UTC));
            tareaDto.setExpirationDate(tareasOptional.get().getFechaVencimiento().atOffset(ZoneOffset.UTC));
            tareaDto.setState(tareasOptional.get().getEstado().getNombre());
            tareaDto.setUserName(AuthUtil.obtenerUsuarioAutenticado());
            tareaDto.setUserRole(usuarioService
                    .findByUsername(AuthUtil.obtenerUsuarioAutenticado()).get().getRole());
            return ResponseEntity.ok(tareaDto);
        }else {
            return ResponseEntity.ok().build();
        }
    }
    public ResponseEntity<StateAppDto> tareasIdDelete(Long tareaId) {

        StateAppDto stateAppDto = new StateAppDto();

        if (tareaRepository.existsById(tareaId)) {
            tareaRepository.deleteById(tareaId);
            stateAppDto = getMessage(tareaId, true);
        }else{
            stateAppDto = getMessage(tareaId, false);
        }

        return ResponseEntity.ok(stateAppDto);
    }
}
