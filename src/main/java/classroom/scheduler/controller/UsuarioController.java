package classroom.scheduler.controller;


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

    @GetMapping("/buscar/nome")
    public ResponseEntity<UsuarioDTO> buscaUsuarioNome(@RequestBody UsuarioDTO dto) { return service.buscarUsuarioNome(dto.nome()); }

    @GetMapping("/buscar/email")
    public ResponseEntity<UsuarioDTO> buscaUsuarioEmail(@RequestBody UsuarioDTO dto) { return service.buscarUsuarioEmail(dto.email()); }

    @DeleteMapping("/deletar/nome")
    public ResponseEntity<String> deletarUsuarioNome(@RequestBody UsuarioDTO dto) { return service.deletarUsuarioNome(dto.nome()); }

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO dto) {
        return service.criarUsuario(dto);
    }

}
