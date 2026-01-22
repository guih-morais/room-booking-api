package classroom.scheduler.service;

import classroom.scheduler.dto.AtualizaUsuarioDTO;
import classroom.scheduler.dto.InputUsuarioDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.exceptions.UsuarioNaoLocalizadoException;
import classroom.scheduler.validacoes.*;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;

    @Transactional
    public UsuarioDTO criarUsuario(InputUsuarioDTO dto) {
        ValidacoesUsuario.validaNomeUsuarioJaExistente(dto.nome(), repositorio);
        ValidacoesUsuario.validaEmailJaExistente(dto.email(), repositorio);
        Usuario usuario = new Usuario(dto);
        repositorio.save(usuario);
        return new UsuarioDTO(usuario);
    }


    public UsuarioDTO buscarUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        return new UsuarioDTO(usuario);
    }

    public List<UsuarioDTO> buscarTodosUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }
    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        repositorio.delete(usuario);
    }

    @Transactional
    public UsuarioDTO editarUsuario(AtualizaUsuarioDTO dto) {
        Usuario usuario = repositorio.findById(dto.id())
                .orElseThrow(UsuarioNaoLocalizadoException::new);

        ValidacoesUsuario.validaNomeUsuarioJaExistente(dto.nome(), repositorio);

        usuario.setNome(dto.nome());
        return new UsuarioDTO(usuario);

    }


}
