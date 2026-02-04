package classroom.scheduler.dto.updates;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AtualizaReservaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime inicioReserva,
        @JsonFormat(pattern = "dd/MM/yy - HH:mm")
        LocalDateTime fimReserva
) {
    public AtualizaReservaDTO{
        Validacao.validaCampoNulo(id, "id");
        Validacao.validaCampoNulo(inicioReserva, "início reserva");
        Validacao.validaCampoNulo(fimReserva, "fim reserva");
    }
    public AtualizaReservaDTO(Reserva reserva) {
        this(   reserva.getId(),
                reserva.getInicioReserva(),
                reserva.getFimReserva());
    }


}
