package pe.edu.idat.portalproveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.idat.portalproveedores.entity.Proveedor;
import pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor;

import java.util.Optional;

public interface ProveedorRepository
        extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByRuc(String ruc);

    Optional<Proveedor> findByUsuarioUsername(
            String username
    );

    long countByEstado(
            EstadoProveedor estado
    );

}