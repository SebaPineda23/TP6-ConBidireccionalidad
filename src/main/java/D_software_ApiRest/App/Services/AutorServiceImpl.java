package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Repositories.AutorRepository;
import D_software_ApiRest.App.Repositories.BaseRepository;
import D_software_ApiRest.App.Repositories.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class AutorServiceImpl extends BaseServiceImpl<Autor, Long> implements AutorService{

    public AutorServiceImpl(BaseRepository<Autor, Long> baseRepository){
        super(baseRepository);
    }

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional
    public Autor update(Long id, Autor entity) throws Exception {
        try{
            Optional<Autor> autorOptional = autorRepository.findById(id);
            if (autorOptional.isPresent()){
                Autor articuloActualizado = autorOptional.get();
            if(entity.getNombre()!=null){
                articuloActualizado.setNombre(entity.getNombre());
            }
            if (entity.getApellido()!=null){
                articuloActualizado.setApellido(entity.getApellido());
            }
            if(entity.getBiografia()!=null){
                articuloActualizado.setBiografia(entity.getBiografia());
            }
                return autorRepository.save(articuloActualizado);
            }else{
                throw new Exception("No se encontro un autor con id igual a "+id);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un autor con el ID " + autorId));

        // Desvincular los libros del autor (opcional si quieres eliminar la relación)
        for (Libro libro : autor.getLibros()) {
            libro.setAutor(null);
            libroRepository.save(libro);
        }

        // Eliminar el autor sin borrar los libros
        autorRepository.deleteById(autorId);
        return true;
    }
    @Transactional
    public Autor vincularLibro(Long autorId, Long libroId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un autor con el ID " + autorId));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un libro con ID " + libroId));

        // Verificar si el libro ya está vinculado al autor
        if (autor.getLibros().contains(libro)) {
            throw new IllegalArgumentException("El libro con ID "+libroId+" ya está vinculado al autor con ID "+autorId);
        }

        // Vincular el libro al autor
        autor.getLibros().add(libro);
        libro.getAutores().add(autor);

        return autorRepository.save(autor);
    }
}
