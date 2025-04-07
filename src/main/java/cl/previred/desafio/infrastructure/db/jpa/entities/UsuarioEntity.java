package cl.previred.desafio.infrastructure.db.jpa.entities;

import cl.previred.desafio.domain.models.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private String role;

    public static UsuarioEntity fromDomain(Usuario domain){
        return UsuarioEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .password(domain.getPassword())
                .enabled(domain.isEnabled())
                .role(domain.getRole())
                .build();
    }
    public Usuario toDomain(){
        return Usuario.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .enabled(this.enabled)
                .role(this.role)
                .build();
    }

}
