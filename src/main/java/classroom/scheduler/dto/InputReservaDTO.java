package classroom.scheduler.dto;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesReserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record InputReservaDTO(
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva,
        Long sala_id,
        Long usuario_id) {

    public InputReservaDTO {
        Validacao.validaCampoNulo(inicioReserva, "início reserva");
        Validacao.validaCampoNulo(fimReserva, "fim reserva");
        Validacao.validaCampoNulo(sala_id, "sala id");
        Validacao.validaCampoNulo(usuario_id, "usuário id");
        ValidacoesReserva.validaInicioReservaAntesDeFimReserva(inicioReserva, fimReserva);
        ValidacoesReserva.validaInicioReservaAntesHoraAtual(inicioReserva);
    }

    public InputReservaDTO(Reserva reserva) {
        this(reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala().getId(),
                reserva.getUsuario().getId());
    }
}
