package classroom.scheduler.controller;


import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.service.SalaService;
import classroom.scheduler.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    SalaService service;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> exibirUsuarios() {
        return service.buscarTodasSalas();
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<SalaDTO> buscaUsuarioNome(@RequestBody SalaDTO dto) { return service.buscarNumeroSala(dto.numeroSala()); }

    @PostMapping("/criar")
    public ResponseEntity<SalaDTO> criarUsuario(@RequestBody SalaDTO dto) { return service.criarSala(dto); }

    @DeleteMapping("deletar/numero")
    public ResponseEntity<String> deletarSalaNumero(@RequestBody SalaDTO dto) { return service.deletarSalaNumero(dto.numeroSala()); }

}
