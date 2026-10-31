package pe.edu.idat.portalproveedores.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.idat.portalproveedores.entity.Proveedor;
import pe.edu.idat.portalproveedores.service.SolicitudCompraService;
import pe.edu.idat.portalproveedores.service.UsuarioService;

@Controller
@RequiredArgsConstructor
public class ProveedorController {

    private final UsuarioService usuarioService;
    private final SolicitudCompraService solicitudCompraService;
    @GetMapping("/proveedor/home")
    public String homeProveedor(
            Authentication authentication,
            Model model){

        Proveedor proveedor =
                usuarioService
                        .obtenerProveedorPorUsername(
                                authentication.getName()
                        );

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "proveedor-home";
    }
    @GetMapping("/proveedor/solicitudes")
    public String verSolicitudes(Model model){

        model.addAttribute(
                "solicitudes",
                solicitudCompraService.listarSolicitudes()
        );

        return "proveedor-solicitudes";
    }
}