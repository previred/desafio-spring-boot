package gestion.tareas.demo.application.post;

import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.db.TareaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostTareaGateway {
    private final PostTareaService postTareaService;

    public ResponseEntity<StateAppDto> tareasPost(TareaRequestDto tareaRequestDto) {
        return  postTareaService.tareasPost(tareaRequestDto);
    }
}
