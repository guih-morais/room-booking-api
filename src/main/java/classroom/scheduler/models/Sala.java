package classroom.scheduler.models;

import classroom.scheduler.dto.SalaDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salas")
public class Sala implements Validavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacidade;
    private int numeroSala;
    private boolean salaAtiva;

    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas;

    public Sala() {
    }

    public Sala(SalaDTO dto) {
        this.capacidade = dto.capacidade();
        this.numeroSala = dto.numeroSala();
        this.salaAtiva = true;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public boolean isSalaAtiva() {
        return salaAtiva;
    }
}
