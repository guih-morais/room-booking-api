package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputReservaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaReservaDTO;
import com.guih.morais.room.booking.exceptions.ReservaNaoLocalizadaException;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Reserva;
import com.guih.morais.room.booking.models.StatusReserva;
import com.guih.morais.room.booking.repository.ReservaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ValidacoesReserva {

    private final ReservaRepository reservaRepository;
    private final ValidacoesUsuario validacoesUsuario;
    private final ValidacoesSala validacoesSala;


    public ValidacoesReserva(ReservaRepository reservaRepository,
                             ValidacoesSala validacoesSala,
                             ValidacoesUsuario validacoesUsuario
    ) {
        this.reservaRepository = reservaRepository;
        this.validacoesSala = validacoesSala;
        this.validacoesUsuario = validacoesUsuario;
    }

    public void validaCriacaoReserva(InputReservaDTO dto) {
        validaInicioReservaAntesDeFimReserva(dto.inicioReserva(), dto.fimReserva());
        validaInicioReservaAntesHoraAtual(dto.inicioReserva());
        validacoesUsuario.validaUsuarioAtivo(dto.usuario_id());
        validacoesSala.validaSalaAtiva(dto.sala_id());
        validaPeriodoEntreReservas(null, dto.inicioReserva(), dto.fimReserva(), dto.sala_id());

        regraNegocioAntecedencia(dto.inicioReserva());
        regraNegocioHorarios30Minutos(dto.inicioReserva(),  dto.fimReserva());
    }

    public void validaEdicaoReserva(AtualizaReservaDTO dto) {
        Reserva reserva = reservaRepository.findById(dto.id()).get();
        validaInicioReservaAntesDeFimReserva(dto.inicioReserva(), dto.fimReserva());
        validaInicioReservaAntesHoraAtual(dto.inicioReserva());
        validaReservaAtiva(dto.id());

        validaPeriodoEntreReservas(reserva.getId(), dto.inicioReserva(), dto.fimReserva(), reserva.getSala().getId());
        regraNegocioAntecedencia(dto.inicioReserva());
        regraNegocioHorarios30Minutos(dto.inicioReserva(), dto.fimReserva());
    }

    public void exclusaoReserva() {

    }

    public void validaReservaAtiva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
        if (reserva.getStatusReserva() != StatusReserva.ATIVA) {
            throw new ValidacaoException("Esta reserva não está ativa, portanto não é possível editá-la ou cancelá-la.");
        }
    }









    private void validaInicioReservaAntesDeFimReserva(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if (inicioReserva.isAfter(fimReserva)) {
            throw new ValidacaoException("O início da reserva não pode ser depois do fim da reserva!");
        }
    }

    private void validaPeriodoEntreReservas(Long id, LocalDateTime inicioReserva, LocalDateTime fimReserva, Long salaId) {
        Optional<Reserva> reservaOptional = reservaRepository.buscaPorIntervaloJaReservado(salaId, inicioReserva, fimReserva);
        if (reservaOptional.isPresent() && !reservaOptional.get().getId().equals(id)) {
            throw new ValidacaoException("Esta sala já foi reservada nesse período de data e horário.");
        }
    }


    private void validaInicioReservaAntesHoraAtual(LocalDateTime inicioReserva) {
        if (inicioReserva.isBefore(LocalDateTime.now())) {
            throw new ValidacaoException("Selecione uma data e horários futuros");
        }
    }

    private void regraNegocioAntecedencia(LocalDateTime inicioReserva) {
        if (LocalDateTime.now().plusHours(3L).minusMinutes(1).isAfter(inicioReserva)) {
            throw new ValidacaoException("Você deve criar uma reserva com no mínimo 3 horas de antecedência");
        }
    }

    private void regraNegocioHorarios30Minutos(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if(!(inicioReserva.getMinute()%30 == 0)) {
            throw new ValidacaoException("As reservas só podem ser realizadas em horários onde a minutagem é 00 ou 30");
        }
        if(!(fimReserva.getMinute()%30 == 0)) {
            throw new ValidacaoException("As reservas só podem ser realizadas em horários onde a minutagem é 00 ou 30");
        }
    }


}
