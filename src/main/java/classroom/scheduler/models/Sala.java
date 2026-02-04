package classroom.scheduler.models;

import classroom.scheduler.dto.input.InputSalaDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salas")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacidade;
    private Integer numeroSala;
    private boolean salaAtiva;

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public void setNumeroSala(Integer numeroSala) {
        this.numeroSala = numeroSala;
    }

    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas;

    public Sala() {
    }

    public Sala(InputSalaDTO dto) {
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

    public void setSalaAtiva(boolean salaAtiva) {
        this.salaAtiva = salaAtiva;
    }

    public Long getId() {
        return id;
    }
}
