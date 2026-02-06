package com.guih.morais.room.booking.dto.resume;

import com.guih.morais.room.booking.models.Usuario;

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
