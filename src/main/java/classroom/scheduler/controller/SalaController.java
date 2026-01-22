package classroom.scheduler.controller;


import classroom.scheduler.dto.updates.AtualizaSalaDTO;
import classroom.scheduler.dto.input.InputSalaDTO;
import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    SalaService service;

    //Requisições POST devem retornar um Location.
    @PostMapping
    public ResponseEntity<SalaDTO> criarSala(@RequestBody InputSalaDTO dto, UriComponentsBuilder uriBuilder) {
        SalaDTO salaDTO = service.criarSala(dto);
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(salaDTO.id()).toUri();
        return ResponseEntity.created(uri).body(salaDTO);
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> exibirSalas() {
        List<SalaDTO> salas = service.buscarTodasSalas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarSala(@PathVariable Long id) {
        SalaDTO sala = service.buscarSala(id);
        return ResponseEntity.ok(sala);
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<HttpStatus> deletarSala(@PathVariable Long id) {
        service.deletarSala(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<SalaDTO> editarSala(@RequestBody AtualizaSalaDTO dto) {
        SalaDTO sala = service.editarSala(dto);
        return ResponseEntity.ok(sala);
    }

}
