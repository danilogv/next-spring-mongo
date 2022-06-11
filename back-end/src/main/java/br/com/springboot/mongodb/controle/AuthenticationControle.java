package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.configuracao.JWT;
import br.com.springboot.mongodb.configuracao.UsuarioConfiguracao;
import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.dto.UsuarioRequisicaoDTO;
import br.com.springboot.mongodb.dto.UsuarioRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin
public class AuthenticationControle {

    @Autowired
    private AuthenticationManager autenticacao;

    @Autowired
    private JWT jwt;

    @Autowired
    private UsuarioConfiguracao configuracao;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequisicaoDTO requisicao) throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Authentication autenticacao = this.autenticacao.authenticate(new UsernamePasswordAuthenticationToken(requisicao.getEmail(),requisicao.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
        User usuario = (User) autenticacao.getPrincipal();
        String token = this.jwt.generateToken(usuario.getUsername());
        UsuarioRespostaDTO resposta = new UsuarioRespostaDTO();
        resposta.setToken(token);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user) {
        Usuario usuario = (Usuario) this.configuracao.loadUserByUsername(user.getName());
        return ResponseEntity.ok(usuario);
    }


}
