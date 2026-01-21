package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.repository.ReservaRepository;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class Validacoes {

    UsuarioRepository usuarioRepository;
    SalaRepository salaRepository;
    ReservaRepository reservaRepository;

    public Validacoes() {
    }
    public Validacoes(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Validacoes(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    Validacoes(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public static void validaCapacidadeSala(Integer capacidade) {
        if(capacidade < 1) {
            throw new ValidacaoException("A capacidade da sala deve ser superior a 0.");
        }
    }

    public static void validaCamposSalaNulo(Integer numeroSala, Integer capacidade) {
        if(numeroSala == null) {
            throw new ValidacaoException("O número da sala não pode ser nulo.");
        }
        if(capacidade == null){
            throw new ValidacaoException("A capacidade da sala não pode ser nula.");
        }
    }

    public void validaNumeroSalaJaExiste(Integer numeroSala) {
        if (salaRepository.findByNumeroSala(numeroSala).isPresent()) {
            throw new ValidacaoException("Já existe outra sala no banco de dados com este número de sala");
        }
    }

}
