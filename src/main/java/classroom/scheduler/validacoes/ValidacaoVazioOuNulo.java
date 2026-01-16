package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.models.Validavel;
import io.micrometer.common.util.StringUtils;

public class ValidacaoVazioOuNulo implements Validacao{
    @Override
    public void validar(Validavel obj) {
        if(obj instanceof Usuario usuario) {
            if(StringUtils.isBlank(usuario.getNome()) ||
                    StringUtils.isBlank(usuario.getEmail())) {
                throw new ValidacaoException("Favor preencher todos os campos.");
            }
        }
        if(obj instanceof Sala sala) {
            if(StringUtils.isBlank(Integer.toString(sala.getCapacidade())) ||
                    StringUtils.isBlank(Integer.toString(sala.getNumeroSala()))) {
                throw new ValidacaoException("Favor preencher todos os campos.");
            }
        }
    }
}
