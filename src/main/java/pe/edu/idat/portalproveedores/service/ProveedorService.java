package pe.edu.idat.portalproveedores.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.dto.RegistroProveedorDTO;
import pe.edu.idat.portalproveedores.entity.Proveedor;
import pe.edu.idat.portalproveedores.entity.Rol;
import pe.edu.idat.portalproveedores.entity.Usuario;
import pe.edu.idat.portalproveedores.entity.enums.EstadoProveedor;
import pe.edu.idat.portalproveedores.exception.CorreoDuplicadoException;
import pe.edu.idat.portalproveedores.exception.RucDuplicadoException;
import pe.edu.idat.portalproveedores.exception.UsuarioDuplicadoException;
import pe.edu.idat.portalproveedores.repository.ProveedorRepository;
import pe.edu.idat.portalproveedores.repository.RolRepository;
import pe.edu.idat.portalproveedores.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProveedorService {
    private static final Logger logger =
            LoggerFactory.getLogger(
                    ProveedorService.class
            );
    private final AuditoriaService auditoriaService;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public void validarRegistro(RegistroProveedorDTO dto){

        if(proveedorRepository.findByRuc(dto.getRuc()).isPresent()){
            throw new RucDuplicadoException(
                    "El RUC ya se encuentra registrado"
            );
        }

        if(usuarioRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new CorreoDuplicadoException(
                    "El correo ya se encuentra registrado"
            );
        }

        if(usuarioRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new UsuarioDuplicadoException(
                    "El usuario ya se encuentra registrado"
            );
        }

    }

    public void registrarProveedor(RegistroProveedorDTO dto){

        validarRegistro(dto);

        Rol rolProveedor = rolRepository
                .findByNombre("PROVEEDOR")
                .orElseThrow(() ->
                        new RuntimeException("Rol PROVEEDOR no encontrado"));

        Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .habilitado(false)
                .intentosFallidos(0)
                .bloqueado(false)
                .fechaRegistro(LocalDateTime.now())
                .rol(rolProveedor)
                .build();

        usuarioRepository.save(usuario);

        Proveedor proveedor = Proveedor.builder()
                .ruc(dto.getRuc())
                .razonSocial(dto.getRazonSocial())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .estado(EstadoProveedor.PENDIENTE)
                .fechaRegistro(LocalDateTime.now())
                .usuario(usuario)
                .build();

        proveedorRepository.save(proveedor);

        logger.info(
                "Nuevo proveedor registrado: {}",
                proveedor.getRazonSocial()
        );

        auditoriaService.registrar(
                "REGISTRO",
                "Proveedor registrado: "
                        + proveedor.getRazonSocial(),
                proveedor.getUsuario().getUsername()

        );
    }
}