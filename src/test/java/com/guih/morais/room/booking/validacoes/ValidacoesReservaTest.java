package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputReservaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaReservaDTO;
import com.guih.morais.room.booking.exceptions.MensagemErro;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Reserva;
import com.guih.morais.room.booking.models.Sala;
import com.guih.morais.room.booking.models.StatusReserva;
import com.guih.morais.room.booking.repository.ReservaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ValidacoesReservaTest {

    @InjectMocks
    ValidacoesReserva validacoesReserva;
    @Mock
    ValidacoesUsuario validacoesUsuario;
    @Mock
    ValidacoesSala validacoesSala;
    @Mock
    Reserva reserva;
    @Mock
    ReservaRepository reservaRepository;
    @Mock
    Sala sala;

    InputReservaDTO dto = new InputReservaDTO(geraInicioReservaValido().plusHours(3), geraInicioReservaValido().plusHours(4), 1L, 1L);


    @Test
    void deveriaPermitirCriacaoReserva() {
        BDDMockito.given(reservaRepository.buscaPorIntervaloJaReservado(dto.sala_id(),
                dto.inicioReserva(),
                dto.fimReserva())).willReturn(Optional.empty());
        BDDMockito
                .doNothing()
                .when(validacoesUsuario).validaUsuarioAtivo(dto.usuario_id());
        BDDMockito
                .doNothing()
                .when(validacoesSala).validaSalaAtiva(dto.sala_id());
        Assertions.assertDoesNotThrow(() -> validacoesReserva.validaCriacaoReserva(dto));

        BDDMockito.then(validacoesUsuario)
                .should()
                .validaUsuarioAtivo(dto.usuario_id());

        BDDMockito.then(validacoesSala)
                .should()
                .validaSalaAtiva(dto.sala_id());
    }

    @Test
    void deveriaLancarExceptionValidacaoPorInicioReservaAntesDoFimReserva() {
        var inicioReserva = geraInicioReservaValido().plusHours(4);
        var fimReserva = inicioReserva.minusHours(1);
        this.dto = new InputReservaDTO(inicioReserva, fimReserva, 1L, 2L);

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));
        Assertions.assertEquals(MensagemErro.RESERVA_INICIO_RESERVA_DEPOIS_QUE_FIM_RESERVA, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoPorInicioReservaAntesDaHoraAtual() {
        var inicioReserva = geraInicioReservaValido().minusHours(1);
        var fimReserva = inicioReserva.plusHours(1);
        this.dto = new InputReservaDTO(inicioReserva, fimReserva, 1L, 2L);

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));
        Assertions.assertEquals(MensagemErro.RESERVA_INICIO_RESERVA_ANTES_QUE_HORA_ATUAL, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoPorUsuarioInativo() {

        BDDMockito.doThrow(new ValidacaoException(MensagemErro.USUARIO_INATIVO))
                .when(validacoesUsuario).validaUsuarioAtivo(dto.usuario_id());

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));

        Assertions.assertEquals(MensagemErro.USUARIO_INATIVO, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoPorSalaInativa() {
        BDDMockito.doThrow(new ValidacaoException(MensagemErro.SALA_INATIVA))
                .when(validacoesSala).validaSalaAtiva(dto.sala_id());

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));

        Assertions.assertEquals(MensagemErro.SALA_INATIVA, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoPorHorarioJaReservado() {
        BDDMockito.given(reservaRepository.buscaPorIntervaloJaReservado(dto.sala_id(),
                dto.inicioReserva(),
                dto.fimReserva())).willReturn(Optional.of(reserva));

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));

        Assertions.assertEquals(MensagemErro.RESERVA_HORARIO_JA_FOI_RESERVADO, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoHorarioSemAntecedenciaMinima() {
        LocalDateTime inicioReserva = dto.inicioReserva().minusHours(3);
        LocalDateTime fimReserva = inicioReserva.plusHours(1);
        this.dto = new InputReservaDTO(inicioReserva, fimReserva, dto.sala_id(), dto.usuario_id());

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));

        Assertions.assertEquals(MensagemErro.RESERVA_NAO_RESPEITA_ANTECEDENCIA_MINIMA, ex.getErro());
    }

    @Test
    void deveriaLancarExceptionValidacaoHorarioForaPadrao() {
        LocalDateTime inicioReserva = dto.inicioReserva().plusMinutes(15);
        LocalDateTime fimReserva = inicioReserva.plusHours(1);
        this.dto = new InputReservaDTO(inicioReserva, fimReserva, dto.sala_id(), dto.usuario_id());

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesReserva.validaCriacaoReserva(dto));

        Assertions.assertEquals(MensagemErro.RESERVA_COM_HORARIO_FORA_DO_PADRAO, ex.getErro());
    }

    @Test
    void deveriaPermitirEdicaoUsuario() {
        AtualizaReservaDTO updateDto = new AtualizaReservaDTO(1L, geraInicioReservaValido().plusHours(3), geraInicioReservaValido().plusHours(4));

        BDDMockito.given(reservaRepository.findById(updateDto.id()))
                .willReturn(Optional.of(reserva));

        BDDMockito.given(reserva.getStatusReserva())
                .willReturn(StatusReserva.ATIVA);

        BDDMockito.given(reserva.getSala())
                .willReturn(sala);

        BDDMockito.given(sala.getId())
                .willReturn(1L);

        BDDMockito.given(reservaRepository.buscaPorIntervaloJaReservado(reserva.getSala().getId(),
                        dto.inicioReserva(),
                        dto.fimReserva()))
                .willReturn(Optional.empty());

        BDDMockito.given(reserva.getId())
                .willReturn(updateDto.id());


        Assertions.assertDoesNotThrow(() -> validacoesReserva.validaEdicaoReserva(updateDto));
    }

    private LocalDateTime geraInicioReservaValido() {
        //Para fins de teste esse metodo sempre retornará uma data/horários válidos, para que seja possível realizar
        //os testes de uma forma adequada das demais validações.
        //Este metodo garante que o início reserva será dentro das específicações de:
        //data Futura e finalizar com a minutagem HH:00 ou HH:30.
        LocalDateTime agora = LocalDateTime.now().withSecond(0).withNano(0);
        int minutos = agora.getMinute();
        int minutosValidos = 30 - (minutos % 30);
        return agora.plusMinutes(minutosValidos);
    }

}