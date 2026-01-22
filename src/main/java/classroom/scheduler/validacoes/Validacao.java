package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import io.micrometer.common.util.StringUtils;

public class Validacao {
    public static void validaCampoNulo(Object objeto, String nomeCampo) {

        if (objeto == null) {
            throw new ValidacaoException("O atributo " + nomeCampo + " não pode ser nulo.");
        }

        if (objeto instanceof String s) {
            if (StringUtils.isBlank(s)) {
                throw new ValidacaoException("O atributo " + nomeCampo + " não pode ser vazio.");
            }
        }

    }
}