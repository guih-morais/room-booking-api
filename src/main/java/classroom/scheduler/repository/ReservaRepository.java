package classroom.scheduler.repository;

import classroom.scheduler.models.Reserva;
import classroom.scheduler.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
