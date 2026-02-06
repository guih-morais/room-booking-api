package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputSalaDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaSalaDTO;
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
        validaTamanhoCapacidadeSala(dto.capacidade());
        validaNumeroSalaJaExiste(dto.numeroSala());
    }

    public void validaEdicaoSala(AtualizaSalaDTO dto) {
        if(dto.capacidade() != null) {
            validaTamanhoCapacidadeSala(dto.capacidade());
        }
        if(dto.numeroSala() != null) {
            validaNumeroSalaJaExiste(dto.numeroSala());
        }
        validaUsuarioPossuiReservaAtivaOuEmAndamento(dto.id());
    }

    public void validaExclusaoSala(Long id) {
        validaSalaAtiva(id);
        validaUsuarioPossuiReservaAtivaOuEmAndamento(id);
    }

    public void validaSalaAtiva(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        if(!sala.isSalaAtiva()) {
            throw new ValidacaoException("Sala está desativada no banco de dados.");
        }
    }


    private void validaTamanhoCapacidadeSala(Integer capacidade) {
        if (capacidade < 1) {
            throw new ValidacaoException("A capacidade da sala deve ser superior a 0.");
        }
    }

    private void validaNumeroSalaJaExiste(Integer numeroSala) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException("Já existe outra sala no banco de dados com este número de sala");
        }
    }

    private void validaUsuarioPossuiReservaAtivaOuEmAndamento(Long id) {
        if (salaRepository.salaPossuiReservaAtivaOuEmAndamento(id)) {
            throw new ValidacaoException("Não é possível desativar ou editar uma sala que possui uma Reserva Ativa ou Em Andamento");
        }
    }

}
