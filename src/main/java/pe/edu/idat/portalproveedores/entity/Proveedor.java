package pe.edu.idat.portalproveedores.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruc;

    @Column(nullable = false)
    private String razonSocial;

    private String telefono;

    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProveedor estado;

    private LocalDateTime fechaRegistro;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "proveedor")
    private List<Propuesta> propuestas;
}