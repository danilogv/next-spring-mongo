package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.padrao_projeto.FacadeRepositorio;
import br.com.springboot.mongodb.utilitario.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServico {

    @Autowired
    private FacadeRepositorio repositorio;

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Usuario usuario) {
        this.validaUsuario(usuario);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        this.repositorio.usuario.save(usuario);
    }

    private void validaUsuario(Usuario usuario) {
        Boolean existeUsuario = this.repositorio.usuario.existsByEmail(usuario.getEmail());

        if (existeUsuario) {
            String msg = "E-mail já cadastrado.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }

        if (!Util.emailValido(usuario.getEmail())) {
            String msg = "CNPJ inválido.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,msg);
        }
    }

}
