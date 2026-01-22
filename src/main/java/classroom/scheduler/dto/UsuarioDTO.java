package classroom.scheduler.dto;

import classroom.scheduler.models.Usuario;
import classroom.scheduler.validacoes.Validacao;
import classroom.scheduler.validacoes.ValidacoesUsuario;

public record UsuarioDTO(

        Long id,
        String nome,
        String email
) {
    public UsuarioDTO {
        Validacao.validaCampoNulo(nome, "nome");
        Validacao.validaCampoNulo(email,"email");
    }
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
