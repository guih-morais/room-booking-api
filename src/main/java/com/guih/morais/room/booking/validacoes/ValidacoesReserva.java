package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputReservaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaReservaDTO;
import com.guih.morais.room.booking.exceptions.MensagemErro;
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
            throw new ValidacaoException(MensagemErro.RESERVA_INATIVA);
        }
    }









    private void validaInicioReservaAntesDeFimReserva(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if (inicioReserva.isAfter(fimReserva)) {
            throw new ValidacaoException(MensagemErro.RESERVA_INICIO_RESERVA_DEPOIS_QUE_FIM_RESERVA);
        }
    }

    private void validaPeriodoEntreReservas(Long id, LocalDateTime inicioReserva, LocalDateTime fimReserva, Long salaId) {
        Optional<Reserva> reservaOptional = reservaRepository.buscaPorIntervaloJaReservado(salaId, inicioReserva, fimReserva);
        if (reservaOptional.isPresent() && !reservaOptional.get().getId().equals(id)) {
            throw new ValidacaoException(MensagemErro.RESERVA_HORARIO_JA_FOI_RESERVADO);
        }
    }


    private void validaInicioReservaAntesHoraAtual(LocalDateTime inicioReserva) {
        if (inicioReserva.isBefore(LocalDateTime.now())) {
            throw new ValidacaoException(MensagemErro.RESERVA_INICIO_RESERVA_ANTES_QUE_HORA_ATUAL);
        }
    }

    private void regraNegocioAntecedencia(LocalDateTime inicioReserva) {
        if (LocalDateTime.now().plusHours(3L).minusMinutes(1).isAfter(inicioReserva)) {
            throw new ValidacaoException(MensagemErro.RESERVA_NAO_RESPEITA_ANTECEDENCIA_MINIMA);
        }
    }

    private void regraNegocioHorarios30Minutos(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if(!(inicioReserva.getMinute()%30 == 0) || !(fimReserva.getMinute()%30 == 0)) {
            throw new ValidacaoException(MensagemErro.RESERVA_COM_HORARIO_FORA_DO_PADRAO);
        }
    }


}
