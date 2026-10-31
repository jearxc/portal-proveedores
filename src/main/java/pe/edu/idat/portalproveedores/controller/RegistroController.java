package pe.edu.idat.portalproveedores.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pe.edu.idat.portalproveedores.dto.RegistroProveedorDTO;
import pe.edu.idat.portalproveedores.service.ProveedorService;

@Controller
@RequiredArgsConstructor
public class RegistroController {

    private final ProveedorService proveedorService;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model){

        model.addAttribute(
                "registroProveedorDTO",
                new RegistroProveedorDTO()
        );

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarProveedor(
            @Valid RegistroProveedorDTO dto,
            BindingResult result,
            Model model){

        System.out.println("ENTRO AL POST");

        if(result.hasErrors()){
            System.out.println("HAY ERRORES");
            return "registro";
        }

        proveedorService.registrarProveedor(dto);

        System.out.println("VALIDACION OK");

        model.addAttribute(
                "mensaje",
                "Registro validado correctamente"
        );

        return "registro";
    }

}