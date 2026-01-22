package classroom.scheduler.validacoes;

import classroom.scheduler.dto.input.InputReservaDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.repository.ReservaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidacoesReserva {
    public static void validaInicioReservaAntesDeFimReserva(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if(inicioReserva.isAfter(fimReserva)) {
            throw new ValidacaoException("O início da reserva não pode ser depois do fim da reserva!");
        }
    }

    public static void validaPeriodoEntreReservas(LocalDateTime inicioReserva, LocalDateTime fimReserva, Long salaId, ReservaRepository repositorio) {
        if(repositorio.intervaloJaReservado(salaId, inicioReserva, fimReserva)) {
            throw new ValidacaoException("Esta sala já foi reservada nesse período de data e horário.");
        }
    }
    public static void validaPeriodoEntreReservas(InputReservaDTO dto, ReservaRepository repositorio) {
        if(repositorio.intervaloJaReservado(dto.sala_id(), dto.inicioReserva(), dto.fimReserva())) {
            throw new ValidacaoException("Esta sala já foi reservada nesse período de data e horário.");
        }
    }

    public static void validaReservaAtiva(Long id, ReservaRepository repositorio) {
        Reserva reserva = repositorio.findById(id)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada no banco de dados."));
        if(reserva.getStatusReserva() == StatusReserva.CANCELADA) {
            throw new ValidacaoException("Esta sala está cancelada, portanto não é possível editá-la ou cancelá-la.");
        }
    }

    public static void validaInicioReservaAntesHoraAtual(LocalDateTime inicioReserva) {
        if(inicioReserva.isBefore(LocalDateTime.now())) {
            throw new ValidacaoException("Você não pode criar uma reserva no passado!");
        }
        if(LocalDateTime.now().plusHours(3L).minusMinutes(1).isAfter(inicioReserva)) {
            throw new ValidacaoException("Você deve criar uma reserva com no mínimo 3 horas de antecedência");
        }
    }


}
