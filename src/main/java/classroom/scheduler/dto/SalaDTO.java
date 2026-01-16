package classroom.scheduler.dto;

import classroom.scheduler.models.Sala;

public record SalaDTO(
        int capacidade,
        int numeroSala
) {

    public static SalaDTO criarDTO(Sala sala) {
        return new SalaDTO(sala.getCapacidade(), sala.getNumeroSala());
    }
}
