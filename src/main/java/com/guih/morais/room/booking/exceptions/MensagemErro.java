package com.guih.morais.room.booking.exceptions;

public enum MensagemErro {

    //Mensagens de Erro para Reserva
    RESERVA_INICIO_RESERVA_DEPOIS_QUE_FIM_RESERVA("O início da reserva não pode ser depois do fim da reserva!"),
    RESERVA_INICIO_RESERVA_ANTES_QUE_HORA_ATUAL("Selecione uma data e horários futuros."),
    RESERVA_INATIVA("Esta reserva não está ativa, portanto não é possível editá-la ou cancelá-la."),
    RESERVA_HORARIO_JA_FOI_RESERVADO("Esta sala já foi reservada nesse período de data e horário."),
    RESERVA_COM_HORARIO_FORA_DO_PADRAO("As reservas só podem ser realizadas em horários onde a minutagem é 00 ou 30."),
    RESERVA_NAO_RESPEITA_ANTECEDENCIA_MINIMA("As reservas devem ser realizadas com no minímo 3 Horas de Antecedência."),

    //Mensagens de Erro para Sala
    SALA_INATIVA("Sala está desativada no banco de dados."),
    SALA_NUMERO_DE_SALA_JA_EXISTENTE("Já existe outra sala no banco de dados com este número de sala"),
    SALA_ESTA_EM_UMA_RESERVA_ATIVA_OU_EM_ANDAMENTO("Não é possível desativar ou editar uma sala que possui uma Reserva Ativa ou Em Andamento"),

    //Mensagens de Erro para Usuario
    USUARIO_NOME_USUARIO_JA_CADASTRADO("Este nome de usuário já está cadastrado no banco de dados."),
    USUARIO_EMAIL_JA_CADASTRADO("Email já cadastrado no banco de dados."),
    USUARIO_INATIVO("Usuário está desativado no banco de dados."),
    USUARIO_ESTA_EM_UMA_RESERVA_ATIVA_OU_EM_ANDAMENTO("Não é possível desativar ou editar um usuário que possui uma Reserva Ativa ou Em Andamento");

    private final String mensagem;

    MensagemErro(String mensagem) {
        this.mensagem = mensagem;
    }
    @Override
    public String toString() {
        return mensagem;
    }
}
