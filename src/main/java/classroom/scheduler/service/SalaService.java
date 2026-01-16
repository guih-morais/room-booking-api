package classroom.scheduler.service;

import classroom.scheduler.dto.SalaDTO;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacaoSalaCapacidade;
import classroom.scheduler.validacoes.ValidacaoSalaNumeroJaExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService {
    @Autowired
    SalaRepository repositorio;

    public ResponseEntity<SalaDTO> criarSala(SalaDTO dto) {
        Sala sala = new Sala(dto);

        List<Validacao> validacoes = new ArrayList<>(List.of(
                new ValidacaoSalaCapacidade(),
                new ValidacaoSalaNumeroJaExistente(repositorio)));

        validacoes.forEach(v -> v.validar(sala));
        repositorio.save(sala);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SalaDTO.criarDTO(sala));
    }

    public ResponseEntity<SalaDTO> buscarNumeroSala(int numeroSala) {
        Sala sala = repositorio.findByNumeroSala(numeroSala).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SalaDTO.criarDTO(sala));
    }

    public ResponseEntity<List<SalaDTO>> buscarTodasSalas() {
        List<Sala> salas = repositorio.findAll();
        List<SalaDTO> salasDTO = salas.stream().map(SalaDTO::criarDTO).toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salasDTO);
    }

    public ResponseEntity<String> deletarSalaNumero(int numeroSala) {
        Sala sala = repositorio.findByNumeroSala(numeroSala).get();
        repositorio.delete(sala);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Sala deletada com sucesso.");
    }


}
