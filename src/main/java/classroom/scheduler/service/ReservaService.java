package classroom.scheduler.service;

import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.models.Validavel;
import classroom.scheduler.repository.ReservaRepository;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import classroom.scheduler.validacoes.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    ReservaRepository repositorio;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SalaRepository salaRepository;

    public ResponseEntity<ReservaDTO> criarReserva(ReservaDTO dto) {

        Usuario usuario = usuarioRepository.findByNome(dto.nome()).get();
        Sala sala = salaRepository.findByNumeroSala(dto.numeroSala()).get();

        Reserva reserva = new Reserva(dto, usuario, sala);
        List<Validacao> validacoes = new ArrayList<>(List.of());
        validacoes.forEach(v -> v.validar(reserva));
        repositorio.save(reserva);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReservaDTO.criarDTO(reserva));
    }

    public ResponseEntity<List<ReservaDTO>> buscarTodasReservas() {
        List<Reserva> reservas = repositorio.findAll();
        List<ReservaDTO> reservasDTO = reservas.stream().map(ReservaDTO::criarDTO).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservasDTO);
    }
}
