package com.guih.morais.room.booking.dto.resume;

import com.guih.morais.room.booking.models.Sala;

public record ResumeSalaDTO(Integer numeroSala) {
    public ResumeSalaDTO(Sala sala) {
        this(sala.getNumeroSala());
    }
}
