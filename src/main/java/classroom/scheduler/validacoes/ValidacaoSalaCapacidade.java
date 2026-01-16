package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.Validavel;

public class ValidacaoSalaCapacidade implements Validacao {
    @Override
    public void validar(Validavel obj){
        Sala sala = (Sala) obj;
        if(sala.getCapacidade() < 1) {
            throw new ValidacaoException("Erro ao criar Sala, a capacidade deve ser superior a 0");
        }
    }
}
