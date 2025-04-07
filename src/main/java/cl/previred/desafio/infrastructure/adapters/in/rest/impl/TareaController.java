package cl.previred.desafio.infrastructure.adapters.in.rest.impl;

import cl.previred.desafio.domain.ports.in.usecases.tarea.IAssignTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.ICreateTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.IDeleteTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.IUpdateTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindAllTareUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByEstadoIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.infrastructure.adapters.in.rest.ITareaController;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaCreateRequest;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaResponse;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tareas")
@RequiredArgsConstructor
public class TareaController implements ITareaController {
    private final ICreateTareaUseCase createTareaUseCase;
    private final IUpdateTareaUseCase updateTareaUseCase;
    private final IFindAllTareUseCase findAllTareaUseCase;
    private final IDeleteTareaUseCase deleteTareaUseCase;
    private final IFindTareaByIdUseCase findTareaByIdUseCase;
    private final IAssignTareaUseCase assignTareaUseCase;
    private final IFindTareaByEstadoIdUseCase findTareaByEstadoIdUseCase;

    @PostMapping
    @Override
    public ResponseEntity<TareaResponse> create(@Valid @RequestBody TareaCreateRequest tareaRequest, Authentication authResult) {
        String username= (String) authResult.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(TareaResponse.fromDomain(createTareaUseCase.execute(tareaRequest.toDomain(),username)));
    }

    @PutMapping
    @Override
    public ResponseEntity<TareaResponse> update(@Valid @RequestBody TareaUpdateRequest tareaUpdateRequest) {
        return ResponseEntity.ok(TareaResponse.fromDomain(updateTareaUseCase.execute(tareaUpdateRequest.toDomain())));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<TareaResponse>> readAll() {
        return ResponseEntity.ok(findAllTareaUseCase.execute()
                .stream()
                .map(TareaResponse::fromDomain)
                .toList());
    }

    @DeleteMapping("{tareaId}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long tareaId) {
        deleteTareaUseCase.execute(tareaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("findById/{tareaId}")
    @Override
    public ResponseEntity<TareaResponse> readById(@PathVariable Long tareaId) {
        return ResponseEntity.ok(TareaResponse.fromDomain(findTareaByIdUseCase.execute(tareaId)));
    }
    @PatchMapping("assign/{tareaId}/{userId}")
    @Override
    public ResponseEntity<Void> assignTarea(@PathVariable Long tareaId, @PathVariable Long userId) {
        assignTareaUseCase.execute(tareaId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("findBy")
    @Override
    public ResponseEntity<List<TareaResponse>> readByEstadoTareaId(@RequestParam Long estadoTareaId) {
        return ResponseEntity.ok(findTareaByEstadoIdUseCase.execute(estadoTareaId)
                .stream()
                .map(TareaResponse::fromDomain)
                .toList());
    }
}
