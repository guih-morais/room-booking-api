package classroom.scheduler.dto.input;

import classroom.scheduler.models.Sala;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InputSalaDTO(
        @Positive
        @NotNull
        Integer capacidade,
        @Positive
        @NotNull
        Integer numeroSala
) {
    public InputSalaDTO(Sala sala) {
        this(sala.getCapacidade(), sala.getNumeroSala());
    }
}
