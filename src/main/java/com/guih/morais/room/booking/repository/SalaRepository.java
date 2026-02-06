package com.guih.morais.room.booking.repository;

import com.guih.morais.room.booking.models.Sala;
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
                  com.guih.morais.room.booking.models.StatusReserva.ATIVA,
                  com.guih.morais.room.booking.models.StatusReserva.EM_ANDAMENTO
              )
            """)
    Boolean salaPossuiReservaAtivaOuEmAndamento(Long id);

}
