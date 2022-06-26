package br.com.springboot.mongodb.controle;

import br.com.springboot.mongodb.configuracao.JWT;
import br.com.springboot.mongodb.configuracao.UsuarioConfiguracao;
import br.com.springboot.mongodb.dominio.Usuario;
import br.com.springboot.mongodb.dto.UsuarioRequisicaoDTO;
import br.com.springboot.mongodb.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UsuarioControle extends ObjetoControle {

    @Autowired
    private UsuarioServico servico;

    @Autowired
    private AuthenticationManager autenticacao;

    @Autowired
    private JWT jwt;

    @Autowired
    private UsuarioConfiguracao configuracao;

    @GetMapping
    public ResponseEntity<Usuario> buscar(Principal user) {
        Usuario usuario = (Usuario) this.configuracao.loadUserByUsername(user.getName());
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioRequisicaoDTO requisicao) {
        Authentication autenticacao = this.autenticacao.authenticate(new UsernamePasswordAuthenticationToken(requisicao.getEmail(),requisicao.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        String token = this.jwt.gerarToken(usuario.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Usuario usuario) {
        try {
            this.servico.inserir(usuario);
        }
        catch (Exception ex) {
            this.geraExcecao(ex);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
