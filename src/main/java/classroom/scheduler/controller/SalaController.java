package classroom.scheduler.controller;


import classroom.scheduler.dto.AtualizaSalaDTO;
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
    public ResponseEntity<List<SalaDTO>> exibirSalas() {
        return service.buscarTodasSalas();
    }

    @GetMapping("/buscar/{numero}")
    public ResponseEntity<SalaDTO> buscarNumeroSala(@PathVariable int numero) { return service.buscarNumeroSala(numero); }

    @PostMapping
    public ResponseEntity<SalaDTO> criarSala(@RequestBody SalaDTO dto) { return service.criarSala(dto); }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable Long id) { return service.deletarSalaNumero(id); }

    @PutMapping
    public ResponseEntity<SalaDTO> editarSala(@RequestBody AtualizaSalaDTO dto) { return service.editarSala(dto); }

}
