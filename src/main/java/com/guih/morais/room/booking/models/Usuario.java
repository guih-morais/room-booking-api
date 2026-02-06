package com.guih.morais.room.booking.models;

import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    @Column(name = "usuario_ativo", nullable = false)
    private boolean usuarioAtivo;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

    public List<Reserva> getReservas() {
        return reservas;
    }

    public Usuario() {
    }

    public Usuario(InputUsuarioDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.usuarioAtivo = true;
    }

    public boolean isUsuarioAtivo() {
        return usuarioAtivo;
    }

    public void setUsuarioAtivo(boolean usuarioAtivo) {
        this.usuarioAtivo = usuarioAtivo;
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
