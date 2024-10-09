package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.*;
import D_software_ApiRest.App.Repositories.BaseRepository;
import D_software_ApiRest.App.Repositories.LocalidadRepository;
import D_software_ApiRest.App.Repositories.PersonaRepository;
import D_software_ApiRest.App.Repositories.DomicilioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<Domicilio, Long> implements DomicilioService{

    public DomicilioServiceImpl(BaseRepository<Domicilio, Long> baseRepository){
        super(baseRepository);
    }

    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private LocalidadRepository localidadRepository;


    @Override
    @Transactional
    public Domicilio update(Long id, Domicilio entity) throws Exception {
        try {
            Optional<Domicilio> domicilioOptional = domicilioRepository.findById(id);
            if (domicilioOptional.isPresent()){
                Domicilio domicilioActualizado = domicilioOptional.get();
            if(entity.getCalle()!= null){
                domicilioActualizado.setCalle(entity.getCalle());
            }
            if(entity.getNumero()!= 0){
                domicilioActualizado.setNumero(entity.getNumero());
            }
                return domicilioRepository.save(domicilioActualizado);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            // Verifica si el domicilio existe
            if(domicilioRepository.existsById(id)) {
                // Encuentra el domicilio
                Domicilio domicilio = domicilioRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Domicilio no encontrado"));

                // Encuentra el cliente asociado al domicilio
                Persona persona = personaRepository.findByDomicilio(domicilio);
                if (persona != null) {
                    // Desasocia el domicilio del cliente
                    persona.setDomicilio(null);
                    personaRepository.save(persona); // Guarda la actualización
                }

                // Elimina el domicilio
                domicilioRepository.delete(domicilio);
                return true;
            } else {
                throw new EntityNotFoundException("Domicilio no encontrado con ID: " + id);
            }
        } catch(Exception e) {
            throw new Exception("Error al eliminar el domicilio: " + e.getMessage());
        }
    }
    @Transactional
    public Domicilio vincularLocalidad(Long domicilioId, Long localidadId) {
        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new EntityNotFoundException("Domicilio no encontrado"));

        Localidad localidad = localidadRepository.findById(localidadId)
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));

        if(domicilio.getLocalidad()!=null && domicilio.getLocalidad().getId().equals(localidadId) ){
            throw new IllegalArgumentException("Ya existe un domicilio con ID"+domicilioId+" vinculado a esta localidad con el ID "+localidadId);
        }

        domicilio.setLocalidad(localidad);
        return domicilioRepository.save(domicilio);
    }
}
