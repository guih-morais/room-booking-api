package classroom.scheduler.repository;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reserva r
    WHERE r.sala.id = :salaId
      AND r.inicioReserva < :fim
      AND r.fimReserva > :inicio
      AND r.statusReserva = classroom.scheduler.models.StatusReserva.ATIVA""")
    Boolean intervaloJaReservado(@Param("salaId") Long salaId,
                                 @Param("inicio") LocalDateTime inicioReserva,
                                 @Param("fim") LocalDateTime fimReserva);
}
