package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Localidad;
import D_software_ApiRest.App.Services.LocalidadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadServiceImpl> {
    @Autowired
    private LocalidadServiceImpl localidadServiceImpl;

}
