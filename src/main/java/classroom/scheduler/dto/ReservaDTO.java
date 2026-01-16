package classroom.scheduler.dto;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;

import java.time.LocalDateTime;

public record ReservaDTO(
        LocalDateTime inicioReserva,
        LocalDateTime fimReserva,
        String nome,
        int numeroSala,
        StatusReserva status
) {

    public static ReservaDTO criarDTO(Reserva reserva) {
        return new ReservaDTO(reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getUsuario().getNome(),
                reserva.getSala().getNumeroSala(),
                reserva.getStatusReserva());
    }

}
