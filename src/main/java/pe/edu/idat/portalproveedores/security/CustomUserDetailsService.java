package pe.edu.idat.portalproveedores.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.idat.portalproveedores.entity.Usuario;
import pe.edu.idat.portalproveedores.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        ));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getHabilitado(),
                true,
                true,
                !usuario.getBloqueado(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" +
                                        usuario.getRol().getNombre()
                        )
                )
        );
    }
}