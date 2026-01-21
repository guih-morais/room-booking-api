package classroom.scheduler.dto;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.validacoes.Validacoes;
import org.springframework.stereotype.Repository;

public record SalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala
) {

    public SalaDTO {
        Validacoes.validaCamposSalaNulo(numeroSala, capacidade);
        Validacoes.validaCapacidadeSala(capacidade);
    }
    public SalaDTO(Sala sala) {
        this(sala.getId(), sala.getCapacidade(), sala.getNumeroSala());
    }
}
