package pe.edu.idat.portalproveedores.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.entity.Auditoria;
import pe.edu.idat.portalproveedores.repository.AuditoriaRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public void registrar(
            String accion,
            String descripcion,
            String usuario){

        Auditoria auditoria =
                Auditoria.builder()
                        .accion(accion)
                        .descripcion(descripcion)
                        .usuario(usuario)
                        .fecha(LocalDateTime.now())
                        .build();

        auditoriaRepository.save(auditoria);
    }
}