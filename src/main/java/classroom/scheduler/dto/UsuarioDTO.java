package classroom.scheduler.dto;

import classroom.scheduler.models.Usuario;
import classroom.scheduler.validacoes.ValidacoesUsuario;

public record UsuarioDTO(

        Long id,
        String nome,
        String email
) {
    public UsuarioDTO {
        ValidacoesUsuario.validaEmailNulo(email);
        ValidacoesUsuario.validaNomeNulo(nome);
    }
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
