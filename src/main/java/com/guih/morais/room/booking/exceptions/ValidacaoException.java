package com.guih.morais.room.booking.exceptions;

public class ValidacaoException extends RuntimeException {

    private final MensagemErro erro;

    public ValidacaoException(MensagemErro erro) {
        super(erro.toString());
        this.erro = erro;
    }

    public MensagemErro getErro() {
        return erro;
    }
}
