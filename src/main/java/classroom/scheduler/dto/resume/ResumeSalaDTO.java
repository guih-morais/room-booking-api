package classroom.scheduler.dto.resume;

import classroom.scheduler.models.Sala;

public record ResumeSalaDTO(Integer numeroSala) {
    public ResumeSalaDTO(Sala sala) {
        this(sala.getNumeroSala());
    }
}
