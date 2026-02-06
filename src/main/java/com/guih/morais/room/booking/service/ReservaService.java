package com.guih.morais.room.booking.service;

import com.guih.morais.room.booking.exceptions.ReservaNaoLocalizadaException;
import com.guih.morais.room.booking.exceptions.SalaNaoLocalizadaException;
import com.guih.morais.room.booking.exceptions.UsuarioNaoLocalizadoException;
import com.guih.morais.room.booking.models.Reserva;
import com.guih.morais.room.booking.models.Sala;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.repository.ReservaRepository;
import com.guih.morais.room.booking.repository.SalaRepository;
import com.guih.morais.room.booking.repository.UsuarioRepository;
import com.guih.morais.room.booking.validacoes.ValidacoesReserva;
import com.guih.morais.room.booking.dto.updates.AtualizaReservaDTO;
import com.guih.morais.room.booking.dto.input.InputReservaDTO;
import com.guih.morais.room.booking.models.StatusReserva;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final ValidacoesReserva validacoesReserva;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          SalaRepository salaRepository,
                          ValidacoesReserva validacoesReserva) {

        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.validacoesReserva = validacoesReserva;
    }

    @Transactional
    public Reserva criarReserva(InputReservaDTO dto) {

        //Tenta buscar Usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(UsuarioNaoLocalizadoException::new);

        //Tenta buscar Sala no banco de dados
        Sala sala = salaRepository.findById(dto.sala_id())
                .orElseThrow(SalaNaoLocalizadaException::new);

        //Validações para criar a Reserva
        validacoesReserva.validaCriacaoReserva(dto);

        Reserva reserva = new Reserva(dto, usuario, sala);
        reservaRepository.save(reserva);
        return reserva;
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
        validacoesReserva.validaReservaAtiva(id);
        reserva.setStatusReserva(StatusReserva.CANCELADA);
    }

    public Page<Reserva> buscarTodasReservas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Transactional
    public Reserva editarReserva(AtualizaReservaDTO dto) {

        Reserva reserva = reservaRepository.findById(dto.id())
                .orElseThrow(ReservaNaoLocalizadaException::new);

        validacoesReserva.validaEdicaoReserva(dto);
        reserva.setInicioReserva(dto.inicioReserva());
        reserva.setFimReserva(dto.fimReserva());
        return reserva;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void atualizarReserva() {
        LocalDateTime agora = LocalDateTime.now();
        List<Reserva> reservas = reservaRepository.buscaReservasPorStatus(StatusReserva.ATIVA);
        reservas.addAll(reservaRepository.buscaReservasPorStatus(StatusReserva.EM_ANDAMENTO));
        reservas.forEach(r -> {
            if(agora.isAfter(r.getFimReserva())) {
                r.setStatusReserva(StatusReserva.FINALIZADA);
            } else if(agora.isAfter(r.getInicioReserva())) {
                r.setStatusReserva(StatusReserva.EM_ANDAMENTO);
            }});
    }
    public Reserva buscarReservaId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
    }
}
