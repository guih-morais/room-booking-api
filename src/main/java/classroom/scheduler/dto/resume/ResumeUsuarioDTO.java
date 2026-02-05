package classroom.scheduler.dto.resume;

import classroom.scheduler.models.Usuario;

public record ResumeUsuarioDTO(
        Long id,
        String nome
) {
    public ResumeUsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome());
    }
}
