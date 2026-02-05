package classroom.scheduler.repository;

import classroom.scheduler.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    Optional<Sala> findByNumeroSala(int numeroSala);
    List<Sala> findBySalaAtivaIsTrue();

    @Query("""
            SELECT COUNT(r) > 0
            FROM Sala s
            JOIN s.reservas r
            WHERE s.id = :id
              AND r.statusReserva IN (
                  classroom.scheduler.models.StatusReserva.ATIVA,
                  classroom.scheduler.models.StatusReserva.EM_ANDAMENTO
              )
            """)
    Boolean salaPossuiReservaAtivaOuEmAndamento(Long id);

}
