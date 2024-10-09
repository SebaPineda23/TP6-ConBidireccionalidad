package D_software_ApiRest.App.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Table(name = "localidad")
public class Localidad extends Base {

    private String denominacion;
    @OneToMany(mappedBy = "localidad",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Domicilio> domicilios = new ArrayList<>();
}
