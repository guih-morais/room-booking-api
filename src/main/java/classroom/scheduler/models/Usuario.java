package classroom.scheduler.models;

import classroom.scheduler.dto.InputUsuarioDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements Validavel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

    public Usuario() {
    }

    public Usuario(InputUsuarioDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
