package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.Validavel;
import classroom.scheduler.repository.SalaRepository;

public class ValidacaoSalaNumeroJaExistente implements Validacao {


    SalaRepository repositorio;

    public ValidacaoSalaNumeroJaExistente(SalaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void validar(Validavel obj) {
        Sala sala = (Sala) obj;
        if (repositorio.findByNumeroSala(sala.getNumeroSala()).isPresent()) {
            throw new ValidacaoException("Já existe uma sala no banco de dados com este número.");
        }
    }
}
