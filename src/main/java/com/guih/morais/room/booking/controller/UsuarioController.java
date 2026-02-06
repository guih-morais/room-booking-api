package com.guih.morais.room.booking.controller;


import com.guih.morais.room.booking.dto.resume.ResumeUsuarioDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.dto.UsuarioDTO;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.service.UsuarioService;
import jakarta.validation.Valid;
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
