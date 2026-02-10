package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputSalaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaSalaDTO;
import com.guih.morais.room.booking.exceptions.MensagemErro;
import com.guih.morais.room.booking.exceptions.SalaNaoLocalizadaException;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Sala;
import com.guih.morais.room.booking.repository.SalaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacoesSala {

    private final SalaRepository salaRepository;

    public ValidacoesSala(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void validaCriacaoSala(InputSalaDTO dto) {
        validaNumeroSalaJaExiste(dto.numeroSala());
    }

    public void validaEdicaoSala(AtualizaSalaDTO dto) {
        if(dto.numeroSala() != null) {
            validaNumeroSalaJaExiste(dto.numeroSala());
        }
        validaSalaAtiva(dto.id());
        validaSalaEstaComReservaAtivaOuEmAndamento(dto.id());
    }

    public void validaExclusaoSala(Long id) {
        validaSalaAtiva(id);
        validaSalaEstaComReservaAtivaOuEmAndamento(id);
    }

    public void validaSalaAtiva(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        if(!sala.isSalaAtiva()) {
            throw new ValidacaoException(MensagemErro.SALA_INATIVA);
        }
    }


    private void validaNumeroSalaJaExiste(Integer numeroSala) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException(MensagemErro.SALA_NUMERO_DE_SALA_JA_EXISTENTE);
        }
    }

    private void validaSalaEstaComReservaAtivaOuEmAndamento(Long id) {
        if (salaRepository.salaPossuiReservaAtivaOuEmAndamento(id)) {
            throw new ValidacaoException(MensagemErro.SALA_ESTA_EM_UMA_RESERVA_ATIVA_OU_EM_ANDAMENTO);
        }
    }

}
