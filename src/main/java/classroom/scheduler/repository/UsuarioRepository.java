package classroom.scheduler.repository;

import classroom.scheduler.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            SELECT COUNT(r) > 0
            FROM Usuario u
            JOIN u.reservas r
            WHERE u.id = :id
              AND r.statusReserva IN (
                  classroom.scheduler.models.StatusReserva.ATIVA,
                  classroom.scheduler.models.StatusReserva.EM_ANDAMENTO
              )
            """)
    Boolean usuarioPossuiReservaAtivaOuEmAndamento(Long id);

    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);
}
