package classroom.scheduler.controller;


import classroom.scheduler.dto.resume.ResumeUsuarioDTO;
import classroom.scheduler.dto.updates.AtualizaUsuarioDTO;
import classroom.scheduler.dto.input.InputUsuarioDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody InputUsuarioDTO inputDTO, UriComponentsBuilder uriBuilder) {
        Usuario usuario = service.criarUsuario(inputDTO);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<List<ResumeUsuarioDTO>> exibirUsuarios() {
        List<Usuario> usuarios = service.buscarTodosUsuarios();
        List<ResumeUsuarioDTO> usuariosDTO = usuarios.stream().map(ResumeUsuarioDTO::new).toList();
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = service.buscarUsuarioPorId(id);
        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletarUsuarioNome(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestBody AtualizaUsuarioDTO dto) {
        Usuario usuario = service.editarUsuario(dto);
        return ResponseEntity.ok(new UsuarioDTO(usuario));
    }

}
