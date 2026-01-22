package classroom.scheduler.controller;

import classroom.scheduler.dto.AtualizaReservaDTO;
import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    ReservaService service;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> exibirReservas() {
        return service.buscarTodasReservas();
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody ReservaDTO dto) {
        return service.criarReserva(dto);
    }

    @DeleteMapping("cancelar/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long id) { return service.cancelarReserva(id); }

    @PutMapping
    public ResponseEntity<ReservaDTO> editarReserva(@RequestBody AtualizaReservaDTO dto) {return service.editarReserva(dto); }
}
