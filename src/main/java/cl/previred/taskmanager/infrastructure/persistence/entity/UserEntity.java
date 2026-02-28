package cl.previred.taskmanager.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    protected UserEntity() {}

    public UserEntity(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Solo necesario si mapper necesita setear ID en updates
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Métodos de actualización controlada
     */
    public void updateCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

}