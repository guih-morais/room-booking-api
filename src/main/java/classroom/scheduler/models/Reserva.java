package classroom.scheduler.models;

import classroom.scheduler.dto.input.InputReservaDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "reservas")
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "salas_id")
    private Sala sala;
    private LocalDateTime inicioReserva;
    private LocalDateTime fimReserva;
    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public Reserva() {
    }

    public LocalDateTime getInicioReserva() {
        return inicioReserva;
    }

    public LocalDateTime getFimReserva() {
        return fimReserva;
    }

    public Reserva(InputReservaDTO dto, Usuario usuario, Sala sala) {
        this.usuario = usuario;
        this.sala = sala;
        this.inicioReserva = dto.inicioReserva();
        this.fimReserva = dto.fimReserva();
        this.statusReserva = StatusReserva.ATIVA;
    }

    public void setInicioReserva(LocalDateTime inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    public void setFimReserva(LocalDateTime fimReserva) {
        this.fimReserva = fimReserva;
    }

    public Long getId() {
        return id;
    }
}