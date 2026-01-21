package classroom.scheduler.dto;

import classroom.scheduler.validacoes.Validacoes;

public record AtualizaSalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala
) {

    public AtualizaSalaDTO {
        if (capacidade != null) {
            Validacoes.validaCapacidadeSala(capacidade);
        }
    }


}
