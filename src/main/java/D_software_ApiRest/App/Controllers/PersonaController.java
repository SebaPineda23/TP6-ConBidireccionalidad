package D_software_ApiRest.App.Controllers;


import D_software_ApiRest.App.Entities.Persona;
import D_software_ApiRest.App.Services.PersonaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/personas")
public class PersonaController extends BaseControllerImpl<Persona, PersonaServiceImpl>{

    @Autowired
    private PersonaServiceImpl personaServiceImpl;

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestParam String filtro){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaServiceImpl.search(filtro));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se obtuvieron resultados.\"}");
        }
    }
    @GetMapping("/searchPaged")
    ResponseEntity<?> search(@RequestParam String filtro, Pageable pageable){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaServiceImpl.search(filtro, pageable));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se obtuvieron resultados.\"}");
        }
    }

    @PostMapping("/{personaId}/addDomicilio/{domicilioId}")
    public ResponseEntity<?> addDomicilio(@PathVariable Long personaId, @PathVariable Long domicilioId) {
        try {
            Persona personaActualizada = personaServiceImpl.addDomicilio(personaId, domicilioId);
            return ResponseEntity.ok(personaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Aquí devolvemos el mensaje original de la excepción
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
