package classroom.scheduler.service;

import classroom.scheduler.dto.AtualizaReservaDTO;
import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.ReservaRepository;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import classroom.scheduler.validacoes.ValidacoesReserva;
import classroom.scheduler.validacoes.ValidacoesSala;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservaService {

    @Autowired
    ReservaRepository repositorio;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SalaRepository salaRepository;

    @Transactional
    public ResponseEntity<ReservaDTO> criarReserva(ReservaDTO dto) {

        //Tenta buscar Usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(() -> new NoSuchElementException("Usuário não localizada no banco de dados."));
        //Tenta buscar Sala no banco de dados
        Sala sala = salaRepository.findById(dto.sala_id())
                .orElseThrow(() -> new NoSuchElementException("Sala não localizada no banco de dados."));

        //Validações para criar a Reserva
        ValidacoesSala.validaSalaAtiva(dto.sala_id(), salaRepository);
        ValidacoesReserva.validaPeriodoEntreReservas(dto, repositorio);

        Reserva reserva = new Reserva(dto, usuario, sala);
        repositorio.save(reserva);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ReservaDTO(reserva));
    }

    @Transactional
    public ResponseEntity<String> cancelarReserva(Long id) {
        Reserva reserva = repositorio.findById(id)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada no banco de dados."));
        ValidacoesReserva.validaReservaAtiva(id, repositorio);
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Reserva cancelada com sucesso!");
    }

    public ResponseEntity<List<ReservaDTO>> buscarTodasReservas() {
        List<Reserva> reservas = repositorio.findAll();
        List<ReservaDTO> reservasDTO = reservas.stream().map(ReservaDTO::new).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservasDTO);
    }

    @Transactional
    public ResponseEntity<ReservaDTO> editarReserva(AtualizaReservaDTO dto) {
        Reserva reserva = repositorio.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada no banco de dados"));
        ValidacoesReserva.validaReservaAtiva(dto.id(), repositorio);
        reserva.setInicioReserva(dto.inicioReserva());
        reserva.setFimReserva(dto.fimReserva());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ReservaDTO(reserva));
    }
}
