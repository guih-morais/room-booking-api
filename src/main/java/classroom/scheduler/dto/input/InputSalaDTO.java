package classroom.scheduler.dto.input;

import classroom.scheduler.models.Sala;
import classroom.scheduler.validacoes.Validacao;

public record InputSalaDTO(
        Integer capacidade,
        Integer numeroSala
) {
    public InputSalaDTO {
        Validacao.validaCampoNulo(capacidade, "capacidade");
        Validacao.validaCampoNulo(numeroSala, "número da sala");
    }
    public InputSalaDTO(Sala sala) {
        this(sala.getCapacidade(), sala.getNumeroSala());
    }
}
