package classroom.scheduler.service;

import classroom.scheduler.dto.AtualizaReservaDTO;
import classroom.scheduler.dto.InputReservaDTO;
import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.dto.UsuarioDTO;
import classroom.scheduler.exceptions.ReservaNaoLocalizadaException;
import classroom.scheduler.exceptions.SalaNaoLocalizadaException;
import classroom.scheduler.exceptions.UsuarioNaoLocalizadoException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ReservaDTO criarReserva(InputReservaDTO dto) {

        //Tenta buscar Usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        //Tenta buscar Sala no banco de dados
        Sala sala = salaRepository.findById(dto.sala_id())
                .orElseThrow(SalaNaoLocalizadaException::new);

        //Validações para criar a Reserva
        ValidacoesSala.validaSalaAtiva(dto.sala_id(), salaRepository);
        ValidacoesReserva.validaPeriodoEntreReservas(dto, repositorio);

        Reserva reserva = new Reserva(dto, usuario, sala);
        repositorio.save(reserva);
        return new ReservaDTO(reserva);
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = repositorio.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
        ValidacoesReserva.validaReservaAtiva(id, repositorio);
        reserva.setStatusReserva(StatusReserva.CANCELADA);
    }

    public Page<ReservaDTO> buscarTodasReservas(Pageable pageable) {
        Page<Reserva> reservas = repositorio.findAll(pageable);
        return reservas.map(ReservaDTO::new);
    }

    @Transactional
    public ReservaDTO editarReserva(AtualizaReservaDTO dto) {
        Reserva reserva = repositorio.findById(dto.id())
                .orElseThrow(ReservaNaoLocalizadaException::new);
        ValidacoesReserva.validaReservaAtiva(dto.id(), repositorio);
        ValidacoesReserva.validaPeriodoEntreReservas(
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala().getId(),
                repositorio);
        reserva.setInicioReserva(dto.inicioReserva());
        reserva.setFimReserva(dto.fimReserva());
        return new ReservaDTO(reserva);
    }

    public ReservaDTO buscarReservaId(Long id) {
        Reserva reserva = repositorio.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
        return new ReservaDTO(reserva);
    }
}
