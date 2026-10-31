package pe.edu.idat.portalproveedores.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.entity.Proveedor;
import pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor;
import pe.edu.idat.portalproveedores.repository.ProveedorRepository;
import pe.edu.idat.portalproveedores.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private static final Logger logger =
            LoggerFactory.getLogger(
                    UsuarioService.class
            );
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaService auditoriaService;
    public void aprobarProveedor(Long proveedorId){

        Proveedor proveedor = proveedorRepository
                .findById(proveedorId)
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no encontrado"));

        proveedor.setEstado(
                EstadoProveedor.APROBADO
        );
        logger.info(
                "Proveedor aprobado: {}",
                proveedor.getRazonSocial()
        );
        auditoriaService.registrar(
                "APROBACION",
                "Proveedor aprobado: " +
                        proveedor.getRazonSocial(),
        "admin");

        proveedor.getUsuario()
                .setHabilitado(true);

        usuarioRepository.save(
                proveedor.getUsuario()
        );

        proveedorRepository.save(
                proveedor
        );
    }
    public Proveedor obtenerProveedorPorUsername(
            String username){

        return proveedorRepository
                .findByUsuarioUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Proveedor no encontrado"
                        ));
    }
    public void rechazarProveedor(Long proveedorId){

        Proveedor proveedor = proveedorRepository
                .findById(proveedorId)
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no encontrado"));

        proveedor.setEstado(
                EstadoProveedor.RECHAZADO
        );

        logger.warn(
                "Proveedor rechazado: {}",
                proveedor.getRazonSocial()
        );

        auditoriaService.registrar(
                "RECHAZO",
                "Proveedor rechazado: " +
                        proveedor.getRazonSocial(),
                "admin"
        );

        proveedor.getUsuario()
                .setHabilitado(false);

        usuarioRepository.save(
                proveedor.getUsuario()
        );

        proveedorRepository.save(
                proveedor
        );
    }
}