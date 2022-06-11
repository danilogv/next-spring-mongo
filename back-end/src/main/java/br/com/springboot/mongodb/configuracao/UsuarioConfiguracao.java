package br.com.springboot.mongodb.configuracao;

import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsuarioConfiguracao implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.repositorio.findByEmail(email);

        if (Objects.isNull(usuario))
            throw new UsernameNotFoundException("Usuário não encontrado.");

        return usuario;
    }

}
