package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputSalaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaSalaDTO;
import com.guih.morais.room.booking.exceptions.MensagemErro;
import com.guih.morais.room.booking.exceptions.SalaNaoLocalizadaException;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Sala;
import com.guih.morais.room.booking.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacoesSalaTest {

    @InjectMocks
    ValidacoesSala validacoesSala;

    @Mock
    SalaRepository salaRepository;

    @Mock
    Sala sala;

    InputSalaDTO dto;

    @Test
    void deveriaCriarSala() {
        this.dto = new InputSalaDTO(4, 2);
        BDDMockito.given(salaRepository.findByNumeroSala(dto.numeroSala()))
                .willReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> validacoesSala.validaCriacaoSala(dto));
    }

    @Test
    void deveriaLancarExceptionNumeroSalaJaExistente() {
        this.dto = new InputSalaDTO(4, 2);
        BDDMockito.given(salaRepository.findByNumeroSala(dto.numeroSala()))
                .willReturn(Optional.of(sala));

        ValidacaoException ex = Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesSala.validaCriacaoSala(dto));
        Assertions.assertEquals(MensagemErro.SALA_NUMERO_DE_SALA_JA_EXISTENTE, ex.getErro());
    }

    @Test
    void deveriaPermitirEditarSala() {
        AtualizaSalaDTO updateDTO = new AtualizaSalaDTO(1L, 4, 2);
        BDDMockito.given(salaRepository.findById(updateDTO.id()))
                .willReturn(Optional.of(sala));
        BDDMockito.given(sala.isSalaAtiva())
                .willReturn(true);
        BDDMockito.given(salaRepository.salaPossuiReservaAtivaOuEmAndamento(updateDTO.id()))
                .willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacoesSala.validaEdicaoSala(updateDTO));
    }

    @Test
    void deveriaLancarExceptionSalaNaoEncontrada() {
        AtualizaSalaDTO updateDTO = new AtualizaSalaDTO(1L, 4, 2);
        BDDMockito.given(salaRepository.findById(updateDTO.id()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(SalaNaoLocalizadaException.class, () -> validacoesSala.validaEdicaoSala(updateDTO));
    }

    @Test
    void deveriaLancarValidacaoExceptionSalaInativa() {
        AtualizaSalaDTO updateDTO = new AtualizaSalaDTO(1L, 4, 2);
        BDDMockito.given(salaRepository.findById(updateDTO.id()))
                .willReturn(Optional.of(sala));
        BDDMockito.given(sala.isSalaAtiva())
                .willReturn(false);

        ValidacaoException ex =  Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesSala.validaEdicaoSala(updateDTO));
        Assertions.assertEquals(MensagemErro.SALA_INATIVA, ex.getErro());
    }

    @Test
    void deveriaLancarValidacaoExceptionSalaComReservaAtivaOuEmAndament() {
        AtualizaSalaDTO updateDTO = new AtualizaSalaDTO(1L, 4, 2);
        BDDMockito.given(salaRepository.findById(updateDTO.id()))
                .willReturn(Optional.of(sala));
        BDDMockito.given(sala.isSalaAtiva())
                .willReturn(true);
        BDDMockito.given(salaRepository.salaPossuiReservaAtivaOuEmAndamento(updateDTO.id()))
                .willReturn(true);

        ValidacaoException ex =  Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesSala.validaEdicaoSala(updateDTO));
        Assertions.assertEquals(MensagemErro.SALA_ESTA_EM_UMA_RESERVA_ATIVA_OU_EM_ANDAMENTO, ex.getErro());
    }

}