package classroom.scheduler.dto;

import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesSala;
import classroom.scheduler.validacoes.ValidacoesUsuario;

public record AtualizaUsuarioDTO(
        Long id,
        String nome
) {
    public AtualizaUsuarioDTO {
        Validacao.validaCampoNulo(id, "id");
        if(nome != null) {
            Validacao.validaCampoNulo(nome, "nome");
        }
    }
}
