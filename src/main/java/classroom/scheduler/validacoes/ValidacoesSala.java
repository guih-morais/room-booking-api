package classroom.scheduler.validacoes;

import classroom.scheduler.dto.input.InputSalaDTO;
import classroom.scheduler.dto.updates.AtualizaReservaDTO;
import classroom.scheduler.dto.updates.AtualizaSalaDTO;
import classroom.scheduler.exceptions.SalaNaoLocalizadaException;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.repository.SalaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacoesSala {

    private final SalaRepository salaRepository;

    public ValidacoesSala(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void validaCriacaoSala(InputSalaDTO dto) {
        validaTamanhoCapacidadeSala(dto.capacidade());
        validaNumeroSalaJaExiste(dto.numeroSala());
    }

    public void validaEdicaoSala(AtualizaSalaDTO dto) {
        if(dto.capacidade() != null) {
            validaTamanhoCapacidadeSala(dto.capacidade());
        }
        if(dto.numeroSala() != null) {
            validaNumeroSalaJaExiste(dto.numeroSala());
        }
    }

    public void validaSalaAtiva(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        if(!sala.isSalaAtiva()) {
            throw new ValidacaoException("Sala está desativada no banco de dados.");
        }
    }


    private void validaTamanhoCapacidadeSala(Integer capacidade) {
        if (capacidade < 1) {
            throw new ValidacaoException("A capacidade da sala deve ser superior a 0.");
        }
    }

    private void validaNumeroSalaJaExiste(Integer numeroSala) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException("Já existe outra sala no banco de dados com este número de sala");
        }
    }

}
