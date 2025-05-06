package dsegovia.desafio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entidad que representa una tarea en el sistema
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarea_seq")
    @SequenceGenerator(name = "tarea_seq", sequenceName = "tarea_seq", initialValue = 5, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad = Prioridad.MEDIA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    private Usuario owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    @ToString.Exclude
    private EstadoTarea estado;

    @Column(name = "etiquetas")
    private String etiquetas;

    // Enumeración de prioridades posibles
    public enum Prioridad {
        BAJA, MEDIA, ALTA, URGENTE
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Tarea tarea = (Tarea) o;
        return getId() != null && Objects.equals(getId(), tarea.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    /**
     * Convierte el string de etiquetas separadas por comas a un conjunto de etiquetas
     * @return Conjunto de etiquetas individuales
     */
    public Set<String> getEtiquetasSet() {
        if (etiquetas == null || etiquetas.trim().isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(etiquetas.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    /**
     * Establece las etiquetas a partir de un conjunto
     * @param etiquetasSet Conjunto de etiquetas
     */
    public void setEtiquetasSet(Set<String> etiquetasSet) {
        if (etiquetasSet == null || etiquetasSet.isEmpty()) {
            this.etiquetas = null;
        } else {
            this.etiquetas = String.join(",", etiquetasSet);
        }
    }
}