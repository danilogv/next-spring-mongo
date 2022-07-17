package br.com.springboot.mongodb.servico;

import br.com.springboot.mongodb.configuracao.UsuarioConfiguracao;
import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.padrao_projeto.FacadeRepositorio;
import br.com.springboot.mongodb.utilitario.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServico {

    @Autowired
    private FacadeRepositorio repositorio;

    @Autowired
    private JavaMailSender configEmail;

    @Autowired
    private UsuarioConfiguracao configuracao;

    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void inserir(Usuario usuario) {
        this.validaUsuario(usuario);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        this.repositorio.usuario.save(usuario);
        this.enviaEmail(usuario.getEmail());
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

    private void enviaEmail(String email) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom("fulano06071988@hotmail.com");
        mensagem.setTo(email);
        mensagem.setSubject("Sys Contábil - Bem Vindo");
        mensagem.setText("Bem vindo ao melhor sistema contábil do país!");
        this.configEmail.send(mensagem);
    }

}
