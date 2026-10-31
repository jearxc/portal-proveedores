package pe.edu.idat.portalproveedores.entity;
import jakarta.persistence.*;
import pe.edu.idat.portalproveedores.enums.EstadoSolicitud;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private String titulo;

    private String descripcion;

    private LocalDateTime fechaPublicacion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;
    @OneToMany(mappedBy = "solicitudCompra")
    private List<Propuesta> propuestas;
}