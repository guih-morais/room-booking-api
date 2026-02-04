package classroom.scheduler.validacoes;

import classroom.scheduler.dto.ReservaDTO;
import classroom.scheduler.dto.input.InputReservaDTO;
import classroom.scheduler.dto.updates.AtualizaReservaDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.repository.ReservaRepository;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidacoesReserva {

    private final ReservaRepository reservaRepository;
    private final ValidacoesUsuario validacoesUsuario;
    private final ValidacoesSala validacoesSala;


    public ValidacoesReserva(ReservaRepository reservaRepository,
                             ValidacoesSala validacoesSala,
                             ValidacoesUsuario validacoesUsuario
    ) {
        this.reservaRepository = reservaRepository;
        this.validacoesSala = validacoesSala;
        this.validacoesUsuario = validacoesUsuario;
    }

    public void validaCriacaoReserva(InputReservaDTO dto) {
        validaInicioReservaAntesDeFimReserva(dto.inicioReserva(), dto.fimReserva());
        validaInicioReservaAntesHoraAtual(dto.inicioReserva());
        validacoesUsuario.validaUsuarioAtivo(dto.usuario_id());
        validacoesSala.validaSalaAtiva(dto.sala_id());
        validaPeriodoEntreReservas(dto);

        regraNegocioAntecedencia(dto.inicioReserva());
    }

    public void validaEdicaoReserva(AtualizaReservaDTO dto) {
        Reserva reserva = reservaRepository.findById(dto.id()).get();
        validaInicioReservaAntesDeFimReserva(dto.inicioReserva(), dto.fimReserva());
        validaInicioReservaAntesHoraAtual(dto.inicioReserva());
        validaReservaAtiva(dto.id());
        validaPeriodoEntreReservas(
                reserva.getInicioReserva(),
                reserva.getFimReserva(),
                reserva.getSala().getId());

        regraNegocioAntecedencia(dto.inicioReserva());
    }

    public void validaReservaAtiva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada no banco de dados."));
        if (reserva.getStatusReserva() == StatusReserva.CANCELADA) {
            throw new ValidacaoException("Esta reserva está cancelada, portanto não é possível editá-la ou cancelá-la.");
        }
    }









    private void validaInicioReservaAntesDeFimReserva(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
        if (inicioReserva.isAfter(fimReserva)) {
            throw new ValidacaoException("O início da reserva não pode ser depois do fim da reserva!");
        }
    }

    private void validaPeriodoEntreReservas(LocalDateTime inicioReserva, LocalDateTime fimReserva, Long salaId) {
        if (reservaRepository.intervaloJaReservado(salaId, inicioReserva, fimReserva)) {
            throw new ValidacaoException("Esta sala já foi reservada nesse período de data e horário.");
        }
    }

    private void validaPeriodoEntreReservas(InputReservaDTO dto) {
        if (reservaRepository.intervaloJaReservado(dto.sala_id(), dto.inicioReserva(), dto.fimReserva())) {
            throw new ValidacaoException("Esta sala já foi reservada nesse período de data e horário.");
        }
    }

    private void validaInicioReservaAntesHoraAtual(LocalDateTime inicioReserva) {
        if (inicioReserva.isBefore(LocalDateTime.now())) {
            throw new ValidacaoException("Selecione uma data e horários futuros");
        }
    }

    private void regraNegocioAntecedencia(LocalDateTime inicioReserva) {
        if (LocalDateTime.now().plusHours(3L).minusMinutes(1).isAfter(inicioReserva)) {
            throw new ValidacaoException("Você deve criar uma reserva com no mínimo 3 horas de antecedência");
        }
    }

}
