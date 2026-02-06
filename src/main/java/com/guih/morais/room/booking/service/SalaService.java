package com.guih.morais.room.booking.service;

import com.guih.morais.room.booking.exceptions.SalaNaoLocalizadaException;
import com.guih.morais.room.booking.repository.SalaRepository;
import com.guih.morais.room.booking.validacoes.ValidacoesSala;
import com.guih.morais.room.booking.dto.updates.AtualizaSalaDTO;
import com.guih.morais.room.booking.dto.input.InputSalaDTO;
import com.guih.morais.room.booking.models.Sala;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository repositorio;
    private final ValidacoesSala validacoesSala;

    public SalaService(SalaRepository repositorio, ValidacoesSala validacoesSala) {
        this.repositorio = repositorio;
        this.validacoesSala = validacoesSala;
    }

    @Transactional
    public Sala criarSala(InputSalaDTO dto) {

        validacoesSala.validaCriacaoSala(dto);

        Sala sala = new Sala(dto);
        repositorio.save(sala);
        return sala;
    }

    public Sala buscarSala(Long id) {
        return repositorio.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
    }

    public List<Sala> buscarTodasSalas() {
        return repositorio.findBySalaAtivaIsTrue();

    }

    @Transactional
    public void deletarSala(Long id) {
        Sala sala = repositorio.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        validacoesSala.validaExclusaoSala(id);
        sala.setSalaAtiva(false);
    }

    @Transactional
    public Sala editarSala(AtualizaSalaDTO dto) {

        Sala sala = repositorio.findById(dto.id())
                .orElseThrow(SalaNaoLocalizadaException::new);

        validacoesSala.validaEdicaoSala(dto);

        if (dto.numeroSala() != null) {
            sala.setNumeroSala(dto.numeroSala());
        }
        if (dto.capacidade() != null) {
            sala.setCapacidade(dto.capacidade());
        }

        return sala;
    }
}
