package cl.previred.desafio.domain.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EstadoTarea {
    private Long id;
    private String nombre;
}
