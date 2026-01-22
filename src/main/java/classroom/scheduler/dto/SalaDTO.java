package classroom.scheduler.dto;

import classroom.scheduler.models.Sala;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesSala;

public record SalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala,
        Boolean salaAtiva
) {
    public SalaDTO(Sala sala) {
        this(sala.getId(), sala.getCapacidade(), sala.getNumeroSala(), sala.isSalaAtiva());
    }
}
