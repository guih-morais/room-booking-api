package classroom.scheduler.dto;

import classroom.scheduler.models.Usuario;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        boolean usuarioAtivo
) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.isUsuarioAtivo());
    }
}
