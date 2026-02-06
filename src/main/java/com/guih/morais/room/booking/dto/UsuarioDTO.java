package com.guih.morais.room.booking.dto;

import com.guih.morais.room.booking.models.Usuario;

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
