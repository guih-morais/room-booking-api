package classroom.scheduler.dto;

import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesSala;

public record AtualizaSalaDTO(
        Long id,
        Integer capacidade,
        Integer numeroSala
) {

    public AtualizaSalaDTO {
        Validacao.validaCampoNulo(id, "id");
        if (capacidade != null) {
            ValidacoesSala.validaTamanhoCapacidadeSala(capacidade);
        }
    }


}
