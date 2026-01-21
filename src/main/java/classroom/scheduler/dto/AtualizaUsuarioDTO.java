package classroom.scheduler.dto;

import classroom.scheduler.validacoes.ValidacoesSala;
import classroom.scheduler.validacoes.ValidacoesUsuario;

public record AtualizaUsuarioDTO(
        Long id,
        String nome,
        String email
) {
    public AtualizaUsuarioDTO {
        if(nome != null) {
            ValidacoesUsuario.validaNomeNulo(nome);
        }
    }
}
