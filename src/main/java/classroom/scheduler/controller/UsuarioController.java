package classroom.scheduler.controller;


import classroom.scheduler.dto.AtualizaUsuarioDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> exibirUsuarios() {
        return service.buscarTodosUsuarios();
    }

    @GetMapping("/buscar/{parametro}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable String parametro) { return service.buscarUsuario(parametro); }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarUsuarioNome(@PathVariable Long id) { return service.deletarUsuario(id); }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO dto) {
        return service.criarUsuario(dto);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestBody AtualizaUsuarioDTO dto) { return service.editarUsuario(dto); }

}
