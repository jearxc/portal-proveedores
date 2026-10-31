package pe.edu.idat.portalproveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.idat.portalproveedores.entity.Auditoria;

public interface AuditoriaRepository
        extends JpaRepository<Auditoria, Long> {
}