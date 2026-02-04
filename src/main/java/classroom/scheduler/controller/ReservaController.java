package classroom.scheduler.controller;

import classroom.scheduler.dto.resume.ResumeReservaDTO;
import classroom.scheduler.dto.updates.AtualizaReservaDTO;
import classroom.scheduler.dto.input.InputReservaDTO;
import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.models.Reserva;
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

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ResumeReservaDTO>> exibirReservas(@PageableDefault(size = 5, sort = "statusReserva") Pageable pageable) {
        Page<Reserva> pages = service.buscarTodasReservas(pageable);
        Page<ResumeReservaDTO> pagesDTO = pages.map(ResumeReservaDTO::new);
        return ResponseEntity.ok(pagesDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> exibirReservaId(@PathVariable Long id) {
        ReservaDTO reservaDTO = new ReservaDTO(service.buscarReservaId(id));
        return ResponseEntity.ok(reservaDTO);
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody InputReservaDTO dto, UriComponentsBuilder uriBuilder) {
        Reserva reserva = service.criarReserva(dto);
        URI uri = uriBuilder.path("reservas/{id}").buildAndExpand(reserva.getId()).toUri();
        return ResponseEntity.created(uri).body(new ReservaDTO(reserva));
    }

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<HttpStatus> cancelarReserva(@PathVariable Long id) {
        service.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody AtualizaReservaDTO dto) {
        Reserva reserva = service.editarReserva(dto);
        return ResponseEntity.ok(new ReservaDTO(reserva));
    }
}
