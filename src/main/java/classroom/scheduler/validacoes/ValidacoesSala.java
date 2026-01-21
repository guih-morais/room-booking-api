package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.repository.SalaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacoesSala {

    public static void validaTamanhoCapacidadeSala(Integer capacidade) {
        if (capacidade < 1) {
            throw new ValidacaoException("A capacidade da sala deve ser superior a 0.");
        }
    }

    public static void validaCapacidadeNula(Integer capacidade) {
        if (capacidade == null) {
            throw new ValidacaoException("A capacidade da sala não pode ser nula.");
        }
    }

    public static void validaNumeroSalaNulo(Integer numeroSala) {
        if (numeroSala == null) {
            throw new ValidacaoException("O número da sala não pode ser nulo.");
        }
    }

    public static void validaNumeroSalaJaExiste(Integer numeroSala, SalaRepository salaRepository) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException("Já existe outra sala no banco de dados com este número de sala");
        }
    }

}
