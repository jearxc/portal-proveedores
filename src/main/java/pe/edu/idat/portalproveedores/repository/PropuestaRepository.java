package pe.edu.idat.portalproveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.idat.portalproveedores.entity.Propuesta;

import java.util.List;

public interface PropuestaRepository
        extends JpaRepository<Propuesta, Long> {

    List<Propuesta>
    findBySolicitudCompraId(
            Long solicitudId
    );

}