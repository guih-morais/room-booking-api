package classroom.scheduler.controller;

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

    @PostMapping("/criar")
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody ReservaDTO dto) {
        return service.criarReserva(dto);
    }


}
