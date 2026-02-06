package com.guih.morais.room.booking.repository;

import com.guih.morais.room.booking.models.Reserva;
import com.guih.morais.room.booking.models.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
    SELECT r
    FROM Reserva r
    WHERE r.sala.id = :salaId
      AND r.inicioReserva < :fim
      AND r.fimReserva > :inicio
      AND r.statusReserva = com.guih.morais.room.booking.models.StatusReserva.ATIVA""")
    Optional<Reserva> buscaPorIntervaloJaReservado(@Param("salaId") Long salaId,
                                                   @Param("inicio") LocalDateTime inicioReserva,
                                                   @Param("fim") LocalDateTime fimReserva);
    @Query("""
    SELECT COUNT(r) > 0
    FROM Reserva r
    WHERE r.usuario.id = :id
      AND (r.statusReserva = com.guih.morais.room.booking.models.StatusReserva.ATIVA
      OR r.statusReserva = com.guih.morais.room.booking.models.StatusReserva.EM_ANDAMENTO)""")
    Boolean reservaEstaAtivaPorUsuario(Long id);

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reserva r
    WHERE r.sala.id = :id
      AND (r.statusReserva = com.guih.morais.room.booking.models.StatusReserva.ATIVA
      OR r.statusReserva = com.guih.morais.room.booking.models.StatusReserva.EM_ANDAMENTO)""")
    Boolean reservaEstaAtivaPorSala(Long id);

    @Query("""
            SELECT r
            FROM Reserva r
            WHERE r.statusReserva = :status""")
    List<Reserva> buscaReservasPorStatus(@Param("status") StatusReserva status);
}
