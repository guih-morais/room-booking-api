package classroom.scheduler.service;

import classroom.scheduler.dto.AtualizaSalaDTO;
import classroom.scheduler.dto.InputSalaDTO;
import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.exceptions.SalaNaoLocalizadaException;
import classroom.scheduler.exceptions.UsuarioNaoLocalizadoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.validacoes.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SalaService {


    @Autowired
    SalaRepository repositorio;

    @Transactional
    public SalaDTO criarSala(InputSalaDTO dto) {
        ValidacoesSala.validaNumeroSalaJaExiste(dto.numeroSala(), repositorio);
        Sala sala = new Sala(dto);
        repositorio.save(sala);
        return new SalaDTO(sala);
    }

    public SalaDTO buscarSala(Long id) {
        Sala sala = repositorio.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        return new SalaDTO(sala);
    }

    public List<SalaDTO> buscarTodasSalas() {
        List<Sala> salas = repositorio.findBySalaAtivaIsTrue();
        return salas.stream().map(SalaDTO::new).toList();

    }

    @Transactional
    public void deletarSalaNumero(Long id) {
        Sala sala = repositorio.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        ValidacoesSala.validaSalaAtiva(id, repositorio);
        sala.setSalaAtiva(false);
    }

    @Transactional
    public SalaDTO editarSala(AtualizaSalaDTO dto) {

        Sala sala = repositorio.findById(dto.id())
                .orElseThrow(SalaNaoLocalizadaException::new);
        ValidacoesSala.validaSalaAtiva(dto.id(), repositorio);

        if (dto.numeroSala() != null) {
            ValidacoesSala.validaNumeroSalaJaExiste(dto.numeroSala(), repositorio);
            sala.setNumeroSala(dto.numeroSala());
        }
        if (dto.capacidade() != null) {
            sala.setCapacidade(dto.capacidade());
        }

        return new SalaDTO(sala);
    }
}
