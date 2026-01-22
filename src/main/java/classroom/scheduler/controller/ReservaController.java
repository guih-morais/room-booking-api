package classroom.scheduler.controller;

import classroom.scheduler.dto.resume.ResumeReservaDTO;
import classroom.scheduler.dto.updates.AtualizaReservaDTO;
import classroom.scheduler.dto.input.InputReservaDTO;
import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    ReservaService service;

    @GetMapping
    public ResponseEntity<Page<ResumeReservaDTO>> exibirReservas(@PageableDefault(size = 5, sort = "statusReserva") Pageable pageable) {
        Page<ResumeReservaDTO> pages = service.buscarTodasReservas(pageable);
        return ResponseEntity.ok(pages);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> exibirReservaId(@PathVariable Long id) {
        ReservaDTO reserva = service.buscarReservaId(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody InputReservaDTO dto, UriComponentsBuilder uriBuilder) {
        ReservaDTO reservaDTO = service.criarReserva(dto);
        URI uri = uriBuilder.path("reservas/{id}").buildAndExpand(reservaDTO.id()).toUri();
        return ResponseEntity.created(uri).body(reservaDTO);
    }

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<HttpStatus> cancelarReserva(@PathVariable Long id) {
        service.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody AtualizaReservaDTO dto) {
        ReservaDTO reserva = service.editarReserva(dto);
        return ResponseEntity.ok(reserva);
    }
}
