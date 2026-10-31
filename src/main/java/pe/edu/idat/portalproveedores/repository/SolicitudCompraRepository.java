package pe.edu.idat.portalproveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.idat.portalproveedores.entity.SolicitudCompra;

public interface SolicitudCompraRepository
        extends JpaRepository<SolicitudCompra, Long> {

}