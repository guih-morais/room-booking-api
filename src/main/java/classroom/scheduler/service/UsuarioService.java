package classroom.scheduler.service;

import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.validacoes.*;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;

    public ResponseEntity<UsuarioDTO> criarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(dto);

        List<Validacao> validacoes = new ArrayList<>(List.of(
                new ValidacaoVazioOuNulo(),
                new ValidacaoUsuarioNomeJaCadastrado(repositorio),
                new ValidacaoUsuarioEmailJaCadastrado(repositorio)));
                validacoes.forEach(v -> v.validar(usuario));

        repositorio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.criarDTO(usuario));
    }


    public ResponseEntity<UsuarioDTO> buscarUsuarioNome(String nome) {
        Usuario usuario = repositorio.findByNome(nome).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UsuarioDTO.criarDTO(usuario));
    }

    public ResponseEntity<List<UsuarioDTO>> buscarTodosUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        List<UsuarioDTO> usuariosdto = usuarios.stream().map(UsuarioDTO::criarDTO).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuariosdto);
    }

    public ResponseEntity<UsuarioDTO> buscarUsuarioEmail(String email) {
        Usuario usuario = repositorio.findByEmail(email).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UsuarioDTO.criarDTO(usuario));
    }

    public ResponseEntity<String> deletarUsuarioNome(String nome) {
        Usuario usuario = repositorio.findByNome(nome).get();
        repositorio.delete(usuario);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Usuário deletado com sucesso!");
    }
}
