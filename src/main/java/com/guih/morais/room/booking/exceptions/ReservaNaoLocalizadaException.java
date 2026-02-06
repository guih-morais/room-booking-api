package com.guih.morais.room.booking.exceptions;

public class ReservaNaoLocalizadaException extends RuntimeException {
    public ReservaNaoLocalizadaException() {
        super("Reserva não localizada no banco de dados.");
    }
}
