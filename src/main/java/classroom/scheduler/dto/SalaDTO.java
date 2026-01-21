package classroom.scheduler.dto;

import classroom.scheduler.models.Sala;
import classroom.scheduler.validacoes.ValidacoesSala;

public record SalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala
) {

    public SalaDTO {
        ValidacoesSala.validaCapacidadeNula(capacidade);
        ValidacoesSala.validaTamanhoCapacidadeSala(capacidade);
        ValidacoesSala.validaNumeroSalaNulo(numeroSala);
    }
    public SalaDTO(Sala sala) {
        this(sala.getId(), sala.getCapacidade(), sala.getNumeroSala());
    }
}
