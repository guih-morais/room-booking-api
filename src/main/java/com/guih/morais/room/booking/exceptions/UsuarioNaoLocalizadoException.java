package com.guih.morais.room.booking.exceptions;

public class UsuarioNaoLocalizadoException extends RuntimeException {
    public UsuarioNaoLocalizadoException() {
        super("Usuário não localizado no banco de dados.");
    }
}
