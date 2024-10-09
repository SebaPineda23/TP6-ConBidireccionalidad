package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Localidad;
import D_software_ApiRest.App.Repositories.BaseRepository;
import D_software_ApiRest.App.Repositories.DomicilioRepository;
import D_software_ApiRest.App.Repositories.LocalidadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements LocalidadService{

    public LocalidadServiceImpl(BaseRepository<Localidad, Long> baseRepository){
        super(baseRepository);
    }

    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Override
    @Transactional
    public Localidad update(Long id, Localidad entity) throws Exception {
        try{
            Optional<Localidad> localidadOptional = localidadRepository.findById(id);
            if(localidadOptional.isPresent()){
                Localidad localidadActualizada = localidadOptional.get();
                if(entity.getDenominacion()!=null){
                    localidadActualizada.setDenominacion(entity.getDenominacion());
                }
                return localidadRepository.save(localidadActualizada);
            }else{
                throw new Exception("No se encontro localidad con el id "+id);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        if (!localidadRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró una localidad con el ID " + id);
        }

        // Eliminar la localidad, lo cual también eliminará los domicilios asociados en cascada.
        localidadRepository.deleteById(id);
        return true;
    }
}
