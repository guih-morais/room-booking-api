package classroom.scheduler.dto.resume;

import classroom.scheduler.models.Usuario;

public record ResumeUsuarioDTO(
        String nome
) {
    public ResumeUsuarioDTO(Usuario usuario) {
        this(usuario.getNome());
    }
}
