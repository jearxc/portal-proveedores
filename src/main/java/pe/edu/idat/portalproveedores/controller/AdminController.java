package pe.edu.idat.portalproveedores.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pe.edu.idat.portalproveedores.repository.AuditoriaRepository;
import pe.edu.idat.portalproveedores.repository.ProveedorRepository;
import pe.edu.idat.portalproveedores.service.UsuarioService;
import pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor;
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProveedorRepository proveedorRepository;
    private final UsuarioService usuarioService;
    private final AuditoriaRepository auditoriaRepository;
    @GetMapping("/admin/proveedores")
    public String listarProveedores(Model model){

        model.addAttribute(
                "proveedores",
                proveedorRepository.findAll()
        );
        model.addAttribute(
                "total",
                proveedorRepository.count()
        );

        model.addAttribute(
                "pendientes",
                proveedorRepository.countByEstado(
                        EstadoProveedor.PENDIENTE
                )
        );

        model.addAttribute(
                "aprobados",
                proveedorRepository.countByEstado(
                        EstadoProveedor.APROBADO
                )
        );

        model.addAttribute(
                "rechazados",
                proveedorRepository.countByEstado(
                        pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor.RECHAZADO
                )
        );

        return "admin-proveedores";
    }

    @PostMapping("/admin/proveedores/aprobar/{id}")
    public String aprobarProveedor(
            @PathVariable Long id){

        usuarioService.aprobarProveedor(id);

        return "redirect:/admin/proveedores";
    }
    @PostMapping("/admin/proveedores/rechazar/{id}")
    public String rechazarProveedor(
            @PathVariable Long id){

        usuarioService.rechazarProveedor(id);

        return "redirect:/admin/proveedores";
    }
    @GetMapping("/admin/auditoria")
    public String verAuditoria(
            Model model){

        model.addAttribute(
                "auditorias",
                auditoriaRepository.findAll()
        );

        return "admin-auditoria";
    }
}