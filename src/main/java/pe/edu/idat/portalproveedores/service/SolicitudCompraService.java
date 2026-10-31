package pe.edu.idat.portalproveedores.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.entity.SolicitudCompra;
import pe.edu.idat.portalproveedores.enums.EstadoSolicitud;
import pe.edu.idat.portalproveedores.repository.SolicitudCompraRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudCompraService {

    private final SolicitudCompraRepository solicitudCompraRepository;

    public void crearSolicitud(
            SolicitudCompra solicitud){

        solicitud.setFechaPublicacion(
                LocalDateTime.now()
        );
        solicitud.setEstado(
                EstadoSolicitud.ABIERTA
        );
        solicitudCompraRepository.save(
                solicitud
        );
    }
    public List<SolicitudCompra> listarSolicitudes(){

        return solicitudCompraRepository.findAll();

    }
    public void cerrarSolicitud(Long id){

        SolicitudCompra solicitud =
                solicitudCompraRepository
                        .findById(id)
                        .orElseThrow();

        solicitud.setEstado(
                EstadoSolicitud.CERRADA
        );

        solicitudCompraRepository.save(
                solicitud
        );
    }
    public void reabrirSolicitud(Long id){

        SolicitudCompra solicitud =
                solicitudCompraRepository
                        .findById(id)
                        .orElseThrow();

        solicitud.setEstado(
                EstadoSolicitud.ABIERTA
        );

        solicitudCompraRepository.save(
                solicitud
        );
    }
    public void eliminarSolicitud(Long id){

        solicitudCompraRepository
                .deleteById(id);
    }
    public SolicitudCompra obtenerPorId(
            Long id){

        return solicitudCompraRepository
                .findById(id)
                .orElseThrow();
    }

}