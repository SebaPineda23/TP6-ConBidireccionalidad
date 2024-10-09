package D_software_ApiRest.App.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Getter
@Setter
@Table(name = "domicilio")
public class Domicilio extends Base {

    private String Calle;
    private int numero;
    @OneToOne(mappedBy = "domicilio")
    @JsonIgnore
    private Persona persona;
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
    @PreRemove
    private void preRemove() {
        // Desvincula la persona antes de eliminar el domicilio
        if (this.persona != null) {
            this.persona.setDomicilio(null);
        }
    }
}
