package D_software_ApiRest.App.Repositories;


import D_software_ApiRest.App.Entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends BaseRepository<Libro, Long> {
    List<Libro> findByPersonaId(Long personaId);
}
