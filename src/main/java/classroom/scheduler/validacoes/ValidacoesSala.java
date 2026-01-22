package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.SalaNaoLocalizadaException;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.repository.SalaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacoesSala {

    public static void validaTamanhoCapacidadeSala(Integer capacidade) {
        if (capacidade < 1) {
            throw new ValidacaoException("A capacidade da sala deve ser superior a 0.");
        }
    }

    public static void validaSalaAtiva(Long id, SalaRepository salaRepository) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(SalaNaoLocalizadaException::new);
        if(!sala.isSalaAtiva()) {
            throw new ValidacaoException("Sala está desativada no banco de dados.");
        }
    }

    public static void validaNumeroSalaJaExiste(Integer numeroSala, SalaRepository salaRepository) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException("Já existe outra sala no banco de dados com este número de sala");
        }
    }

}
