package com.guih.morais.room.booking.dto;

import com.guih.morais.room.booking.models.Sala;

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
