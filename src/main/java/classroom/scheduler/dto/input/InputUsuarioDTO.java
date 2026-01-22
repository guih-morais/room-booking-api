package classroom.scheduler.dto.input;

import classroom.scheduler.models.Usuario;
import classroom.scheduler.validacoes.Validacao;

public record InputUsuarioDTO(
        String nome,
        String email
) {
    public InputUsuarioDTO {
        Validacao.validaCampoNulo(nome, "nome");
        Validacao.validaCampoNulo(email,"email");
    }
}
