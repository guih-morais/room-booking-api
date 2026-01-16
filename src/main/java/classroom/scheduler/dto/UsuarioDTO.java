package classroom.scheduler.dto;

import classroom.scheduler.models.Usuario;

public record UsuarioDTO(
        String nome,
        String email
) {
    public static UsuarioDTO criarDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getEmail());
    }
}
