package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonaService extends BaseService<Persona, Long>{
    List<Persona> search(String filtro) throws Exception;
    Page<Persona> search(String filtro, Pageable pageable) throws Exception;
}
