package classroom.scheduler.dto;

import classroom.scheduler.validacoes.ValidacoesSala;

public record AtualizaSalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala
) {

    public AtualizaSalaDTO {
        if (capacidade != null) {
            ValidacoesSala.validaTamanhoCapacidadeSala(capacidade);
        }
    }


}
