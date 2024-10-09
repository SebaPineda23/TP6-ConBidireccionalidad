package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Domicilio;
import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Entities.Persona;
import D_software_ApiRest.App.Repositories.BaseRepository;
import D_software_ApiRest.App.Repositories.PersonaRepository;
import D_software_ApiRest.App.Repositories.DomicilioRepository;
import D_software_ApiRest.App.Repositories.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl extends BaseServiceImpl<Persona, Long> implements PersonaService{

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private LibroRepository libroRepository;

    public PersonaServiceImpl(BaseRepository<Persona, Long> baseRepository){
        super(baseRepository);
    }
    @Override
    @Transactional
    public Persona update(Long id, Persona entity) throws Exception {
        try{
            Optional<Persona> personaOptional = personaRepository.findById(id);
            if (personaOptional.isPresent()) {
                Persona personaActualizada = personaOptional.get();

                // Actualizar solo los atributos que no son null en el entity recibido
                if (entity.getNombre() != null) {
                    personaActualizada.setNombre(entity.getNombre());
                }
                if (entity.getApellido() != null) {
                    personaActualizada.setApellido(entity.getApellido());
                }
                if (entity.getDni() != 0) {
                    personaActualizada.setDni(entity.getDni());
                }
                return personaRepository.save(personaActualizada);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional
    public boolean delete(Long personaId) throws Exception {
        if (!personaRepository.existsById(personaId)) {
            throw new EntityNotFoundException("No se encontró a una persona con ID: " + personaId);
        }

        // Obtener todas las facturas asociadas al cliente específico
        List<Libro> libros = libroRepository.findByPersonaId(personaId); // Usa el método específico que implementaste

        for (Libro libro : libros) {
            libro.setPersona(null); // Desvincular el cliente
        }

        // Guardar todas las facturas actualizadas de una sola vez
        libroRepository.saveAll(libros);

        // Eliminar el cliente
        personaRepository.deleteById(personaId);
        return true;
    }

    @Transactional
    public Persona addDomicilio(Long personaId, Long domicilioId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró una persona con el id " + personaId));
        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un domicilio con el id " + domicilioId));

        // Verificar si la persona ya tiene un domicilio vinculado
        if (persona.getDomicilio() != null && persona.getDomicilio().getId().equals(domicilioId)) {
            throw new IllegalArgumentException("La persona con ID "+personaId+" ya tiene el domicilio vinculado con ID "+domicilioId);
        }

        persona.setDomicilio(domicilio);
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> search(String filtro) throws Exception {
        try{
//            List<Persona> personas = personaRepository.findByNombreContainingOrApellidoContaining(filtro, filtro);
//            List<Persona> personas = personaRepository.search(filtro);
            List<Persona> personas = personaRepository.searchNativo(filtro);
            return personas;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Persona> search(String filtro, Pageable pageable) throws Exception {
        try{
//            Page<Persona> personas = personaRepository.findByNombreContainingOrApellidoContaining(filtro, filtro, pageable);
//            Page<Persona> personas = personaRepository.search(filtro, pageable);
            Page<Persona> personas = personaRepository.searchNativo(filtro, pageable);
            return personas;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
