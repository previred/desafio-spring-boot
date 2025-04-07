package cl.previred.desafio.domain.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private String role;
}
