package D_software_ApiRest.App.Controllers;


import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Services.LibroServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl>{

    @Autowired
    private LibroServiceImpl libroServiceImpl;

    @PostMapping("/{libroId}/addPersona/{personaId}")
    public ResponseEntity<?> addPersona(@PathVariable Long libroId,  @PathVariable Long personaId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroServiceImpl.addPersona(libroId, personaId));
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
