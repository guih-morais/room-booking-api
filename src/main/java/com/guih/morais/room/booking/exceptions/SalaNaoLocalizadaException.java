package com.guih.morais.room.booking.exceptions;

public class SalaNaoLocalizadaException extends RuntimeException {
    public SalaNaoLocalizadaException() {
        super("Sala não localizada no banco de dados.");
    }
}
