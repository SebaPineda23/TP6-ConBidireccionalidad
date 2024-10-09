package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Domicilio;
import D_software_ApiRest.App.Services.DomicilioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/domicilios")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl>{

    @Autowired
    private DomicilioServiceImpl domicilioServiceImpl;

    @PostMapping("/{domicilioId}/addLocalidad/{localidadId}")
    public ResponseEntity<Domicilio> vincularLocalidad(
            @PathVariable Long domicilioId,
            @PathVariable Long localidadId) {
        Domicilio domicilioActualizado = domicilioServiceImpl.vincularLocalidad(domicilioId, localidadId);
        return ResponseEntity.ok(domicilioActualizado);
    }
}
