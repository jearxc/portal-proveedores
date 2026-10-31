package pe.edu.idat.portalproveedores.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.entity.Propuesta;
import pe.edu.idat.portalproveedores.repository.PropuestaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropuestaService {

    private final PropuestaRepository propuestaRepository;

    public void guardarPropuesta(
            Propuesta propuesta){

        propuestaRepository.save(
                propuesta
        );
    }
    public List<Propuesta>
    listarPorSolicitud(Long solicitudId){

        return propuestaRepository
                .findBySolicitudCompraId(
                        solicitudId
                );
    }
    public Propuesta obtenerPorId(Long id){

        return propuestaRepository
                .findById(id)
                .orElseThrow();

    }

}