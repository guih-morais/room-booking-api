package classroom.scheduler.dto;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesReserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jshell.Snippet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva,
        Long sala_id,
        Long usuario_id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        StatusReserva statusReserva

) {

    public ReservaDTO {
        Validacao.validaCampoNulo(inicioReserva, "início reserva");
        Validacao.validaCampoNulo(fimReserva, "fim reserva");
        Validacao.validaCampoNulo(sala_id, "sala id");
        Validacao.validaCampoNulo(usuario_id, "usuário id");
        ValidacoesReserva.validaInicioReservaAntesDeFimReserva(inicioReserva, fimReserva);
        ValidacoesReserva.validaInicioReservaAntesHoraAtual(inicioReserva);
    }

    public ReservaDTO(Reserva reserva) {
        this(reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala().getId(),
                reserva.getUsuario().getId(),
                reserva.getStatusReserva());
    }
}
