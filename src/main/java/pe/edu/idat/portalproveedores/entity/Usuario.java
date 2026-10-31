package pe.edu.idat.portalproveedores.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean habilitado;

    private Integer intentosFallidos;

    private Boolean bloqueado;

    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}