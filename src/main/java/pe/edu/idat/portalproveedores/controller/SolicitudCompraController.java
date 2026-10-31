package pe.edu.idat.portalproveedores.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.idat.portalproveedores.entity.Propuesta;
import pe.edu.idat.portalproveedores.entity.Proveedor;
import pe.edu.idat.portalproveedores.entity.SolicitudCompra;
import pe.edu.idat.portalproveedores.service.PropuestaService;
import pe.edu.idat.portalproveedores.service.SolicitudCompraService;
import pe.edu.idat.portalproveedores.service.UsuarioService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


@Controller
@RequiredArgsConstructor
public class SolicitudCompraController {

    private final SolicitudCompraService solicitudCompraService;
    private final PropuestaService propuestaService;
    private final UsuarioService usuarioService;


    @GetMapping("/admin/solicitudes/nueva")
    public String mostrarFormulario(Model model){

        model.addAttribute(
                "solicitudCompra",
                new SolicitudCompra()
        );

        return "nueva-solicitud";
    }

    @PostMapping("/admin/solicitudes")
    public String guardarSolicitud(
            SolicitudCompra solicitudCompra){

        solicitudCompraService.crearSolicitud(
                solicitudCompra
        );

        return "redirect:/admin/solicitudes?success";
    }
    @GetMapping("/admin/solicitudes")
    public String listarSolicitudes(Model model){

        model.addAttribute(
                "solicitudes",
                solicitudCompraService.listarSolicitudes()
        );

        return "admin-solicitudes";
    }
    @PostMapping("/admin/solicitudes/cerrar/{id}")
    public String cerrarSolicitud(
            @PathVariable Long id){

        solicitudCompraService.cerrarSolicitud(id);

        return "redirect:/admin/solicitudes";
    }
    @PostMapping("/admin/solicitudes/reabrir/{id}")
    public String reabrirSolicitud(
            @PathVariable Long id){

        solicitudCompraService.reabrirSolicitud(id);

        return "redirect:/admin/solicitudes";
    }
    @PostMapping("/admin/solicitudes/eliminar/{id}")
    public String eliminarSolicitud(
            @PathVariable Long id){

        solicitudCompraService.eliminarSolicitud(id);

        return "redirect:/admin/solicitudes";
    }
    @GetMapping("/admin/solicitudes/{id}")
    public String verDetalleSolicitud(
            @PathVariable Long id,
            Model model){

        model.addAttribute(
                "solicitud",
                solicitudCompraService.obtenerPorId(id)
        );

        model.addAttribute(
                "propuestas",
                propuestaService
                        .listarPorSolicitud(id)
        );

        return "detalle-solicitud";
    }
    @GetMapping("/proveedor/solicitudes/{id}")
    public String verDetalleProveedor(
            @PathVariable Long id,
            Model model){

        model.addAttribute(
                "solicitud",
                solicitudCompraService.obtenerPorId(id)
        );

        return "detalle-solicitud-proveedor";
    }

    @GetMapping("/proveedor/propuestas/{id}")
    public String mostrarFormularioPropuesta(
            @PathVariable Long id,
            Model model){

        model.addAttribute(
                "solicitud",
                solicitudCompraService.obtenerPorId(id)
        );

        return "enviar-propuesta";
    }
    @PostMapping("/proveedor/propuestas/{id}")
    public String enviarPropuesta(
            @PathVariable Long id,
            @RequestParam("archivo")
            MultipartFile archivo,
            Authentication authentication)
            throws IOException {

        Path carpeta =
                Paths.get("uploads/propuestas");

        Files.createDirectories(carpeta);

        String nombreArchivo =
                archivo.getOriginalFilename();

        Path ruta =
                carpeta.resolve(nombreArchivo);

        Files.copy(
                archivo.getInputStream(),
                ruta,
                StandardCopyOption.REPLACE_EXISTING
        );

        SolicitudCompra solicitud =
                solicitudCompraService.obtenerPorId(id);

        Proveedor proveedor =
                usuarioService
                        .obtenerProveedorPorUsername(
                                authentication.getName()
                        );

        Propuesta propuesta =
                new Propuesta();

        propuesta.setNombreArchivo(
                nombreArchivo
        );

        propuesta.setRutaArchivo(
                ruta.toString()
        );

        propuesta.setFechaEnvio(
                LocalDateTime.now()
        );

        propuesta.setSolicitudCompra(
                solicitud
        );

        propuesta.setProveedor(
                proveedor
        );

        propuestaService.guardarPropuesta(
                propuesta
        );

        return "redirect:/proveedor/solicitudes?success";
    }

    @GetMapping("/admin/propuestas/descargar/{id}")
    public ResponseEntity<Resource>
    descargarPropuesta(
            @PathVariable Long id)
            throws IOException {

        Propuesta propuesta =
                propuestaService.obtenerPorId(id);

        Path ruta =
                Paths.get(
                        propuesta.getRutaArchivo()
                );

        Resource recurso =
                new UrlResource(
                        ruta.toUri()
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                propuesta.getNombreArchivo() +
                                "\""
                )
                .body(recurso);
    }
}