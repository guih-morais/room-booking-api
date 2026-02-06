package com.guih.morais.room.booking.repository;

import com.guih.morais.room.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            SELECT COUNT(r) > 0
            FROM Usuario u
            JOIN u.reservas r
            WHERE u.id = :id
              AND r.statusReserva IN (
                  com.guih.morais.room.booking.models.StatusReserva.ATIVA,
                  com.guih.morais.room.booking.models.StatusReserva.EM_ANDAMENTO
              )
            """)
    Boolean usuarioPossuiReservaAtivaOuEmAndamento(Long id);

    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);
}
