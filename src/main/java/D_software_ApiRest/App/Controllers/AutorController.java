package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Services.AutorServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/autores")
public class AutorController extends BaseControllerImpl<Autor, AutorServiceImpl>{

    @Autowired
    private AutorServiceImpl autorServiceImpl;

    @PostMapping("/{articuloId}/addLibro/{libroId}")
    public ResponseEntity<?> vincularLibro(
            @PathVariable Long articuloId,
            @PathVariable Long libroId) {
        try {
            Autor articuloActualizado = autorServiceImpl.vincularLibro(articuloId, libroId);
            return ResponseEntity.ok(articuloActualizado);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
