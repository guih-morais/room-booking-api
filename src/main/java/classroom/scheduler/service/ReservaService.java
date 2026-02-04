package classroom.scheduler.service;

import classroom.scheduler.dto.updates.AtualizaReservaDTO;
import classroom.scheduler.dto.input.InputReservaDTO;
import classroom.scheduler.exceptions.ReservaNaoLocalizadaException;
import classroom.scheduler.exceptions.SalaNaoLocalizadaException;
import classroom.scheduler.exceptions.UsuarioNaoLocalizadoException;
import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.StatusReserva;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.ReservaRepository;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import classroom.scheduler.validacoes.ValidacoesReserva;
import classroom.scheduler.validacoes.ValidacoesSala;
import classroom.scheduler.validacoes.ValidacoesUsuario;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final ValidacoesReserva validacoesReserva;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          SalaRepository salaRepository,
                          ValidacoesReserva validacoesReserva) {

        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.validacoesReserva = validacoesReserva;
    }

    @Transactional
    public Reserva criarReserva(InputReservaDTO dto) {

        //Tenta buscar Usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(UsuarioNaoLocalizadoException::new);

        //Tenta buscar Sala no banco de dados
        Sala sala = salaRepository.findById(dto.sala_id())
                .orElseThrow(SalaNaoLocalizadaException::new);

        //Validações para criar a Reserva
        validacoesReserva.validaCriacaoReserva(dto);

        Reserva reserva = new Reserva(dto, usuario, sala);
        reservaRepository.save(reserva);
        return reserva;
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
        validacoesReserva.validaReservaAtiva(id);
        reserva.setStatusReserva(StatusReserva.CANCELADA);
    }

    public Page<Reserva> buscarTodasReservas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Transactional
    public Reserva editarReserva(AtualizaReservaDTO dto) {

        Reserva reserva = reservaRepository.findById(dto.id())
                .orElseThrow(ReservaNaoLocalizadaException::new);

        validacoesReserva.validaEdicaoReserva(dto);
        reserva.setInicioReserva(dto.inicioReserva());
        reserva.setFimReserva(dto.fimReserva());
        return reserva;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void atualizarReserva() {
        LocalDateTime agora = LocalDateTime.now();
        List<Reserva> reservas = reservaRepository.buscaReservasPorStatus(StatusReserva.ATIVA);
        reservas.forEach(r -> {
            if(agora.isAfter(r.getFimReserva())) {
                r.setStatusReserva(StatusReserva.FINALIZADA);
            } else if(agora.isAfter(r.getInicioReserva())) {
                r.setStatusReserva(StatusReserva.EM_ANDAMENTO);
            }});
    }
    public Reserva buscarReservaId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(ReservaNaoLocalizadaException::new);
    }
}
