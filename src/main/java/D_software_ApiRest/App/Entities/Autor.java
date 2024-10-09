package D_software_ApiRest.App.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "autor")
public class Autor extends Base {
    private String nombre;
    private String apellido;
    private String biografia;
    @ManyToMany
    @JoinTable(name = "autor_libro",
    joinColumns = @JoinColumn(name = "autor_id"),
            inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private List<Libro> libros = new ArrayList<>();
}
