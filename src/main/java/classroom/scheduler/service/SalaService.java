package classroom.scheduler.service;

import classroom.scheduler.dto.AtualizaSalaDTO;
import classroom.scheduler.dto.SalaDTO;
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
    public ResponseEntity<SalaDTO> criarSala(SalaDTO dto) {
        Sala sala = new Sala(dto);

        ValidacoesSala.validaNumeroSalaJaExiste(dto.numeroSala(), repositorio);

        repositorio.save(sala);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SalaDTO(sala));
    }

    public ResponseEntity<SalaDTO> buscarNumeroSala(int numeroSala) {
        Sala sala = repositorio.findByNumeroSala(numeroSala)
                .orElseThrow(() -> new NoSuchElementException("Nenhuma sala com este número foi localizada no banco de dados."));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SalaDTO(sala));
    }

    public ResponseEntity<List<SalaDTO>> buscarTodasSalas() {
        List<Sala> salas = repositorio.findBySalaAtivaIsTrue();
        List<SalaDTO> salasDTO = salas.stream().map(SalaDTO::new).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salasDTO);
    }

    @Transactional
    public ResponseEntity<String> deletarSalaNumero(Long id) {
        Sala sala = repositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sala não localizada no banco de dados."));
        ValidacoesSala.validaSalaAtiva(id, repositorio);
        sala.setSalaAtiva(false);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Sala deletada com sucesso.");
    }

    @Transactional
    public ResponseEntity<SalaDTO> editarSala(AtualizaSalaDTO dto) {
        Sala sala = repositorio.findById(dto.id())
                .orElseThrow(() -> new NoSuchElementException("Sala não localizada no banco de dados."));
        ValidacoesSala.validaSalaAtiva(dto.id(), repositorio);
        if (dto.numeroSala() != null) {
            ValidacoesSala.validaNumeroSalaJaExiste(dto.numeroSala(), repositorio);
            sala.setNumeroSala(dto.numeroSala());
        }
        if (dto.capacidade() != null) {
            sala.setCapacidade(dto.capacidade());
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new SalaDTO(sala));
    }
}
