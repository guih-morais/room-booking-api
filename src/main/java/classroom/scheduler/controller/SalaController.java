package classroom.scheduler.controller;


import classroom.scheduler.dto.resume.ResumeSalaDTO;
import classroom.scheduler.dto.updates.AtualizaSalaDTO;
import classroom.scheduler.dto.input.InputSalaDTO;
import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.models.Sala;
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

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SalaDTO> criarSala(@RequestBody InputSalaDTO dto, UriComponentsBuilder uriBuilder) {
        Sala sala = service.criarSala(dto);
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getId()).toUri();
        return ResponseEntity.created(uri).body(new SalaDTO(sala));
    }
    @GetMapping
    public ResponseEntity<List<ResumeSalaDTO>> exibirSalas() {
        List<Sala> salas = service.buscarTodasSalas();
        List<ResumeSalaDTO> salasDTO = salas.stream().map(ResumeSalaDTO::new).toList();
        return ResponseEntity.ok(salasDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarSala(@PathVariable Long id) {
        Sala sala = service.buscarSala(id);
        return ResponseEntity.ok(new SalaDTO(sala));
    }
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<HttpStatus> deletarSala(@PathVariable Long id) {
        service.deletarSala(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<SalaDTO> editarSala(@RequestBody AtualizaSalaDTO dto) {

        Sala sala = service.editarSala(dto);
        return ResponseEntity.ok(new SalaDTO(sala));
    }
}
