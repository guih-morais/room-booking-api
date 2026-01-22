package classroom.scheduler.controller;


import classroom.scheduler.dto.AtualizaUsuarioDTO;
import classroom.scheduler.dto.InputUsuarioDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Handler;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;


    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody InputUsuarioDTO inputDTO, UriComponentsBuilder uriBuilder) {
        UsuarioDTO usuario = service.criarUsuario(inputDTO);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> exibirUsuarios() {
        List<UsuarioDTO> usuarios = service.buscarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Long id) {
        UsuarioDTO usuario = service.buscarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarUsuarioNome(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestBody AtualizaUsuarioDTO dto) {
        UsuarioDTO usuario = service.editarUsuario(dto);
        return ResponseEntity.ok(usuario);
    }

}
