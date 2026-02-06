package com.guih.morais.room.booking.service;

import com.guih.morais.room.booking.exceptions.UsuarioNaoLocalizadoException;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.repository.UsuarioRepository;
import com.guih.morais.room.booking.validacoes.ValidacoesUsuario;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.validacoes.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repositorio;
    private final ValidacoesUsuario validacoesUsuario;

    public UsuarioService(UsuarioRepository repositorio, ValidacoesUsuario validacoesUsuario) {
        this.repositorio = repositorio;
        this.validacoesUsuario = validacoesUsuario;
    }

    @Transactional
    public Usuario criarUsuario(InputUsuarioDTO dto) {
        validacoesUsuario.validaCriacaoUsuario(dto);
        Usuario usuario = new Usuario(dto);
        repositorio.save(usuario);
        return usuario;
    }


    public Usuario buscarUsuarioPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return repositorio.findAll();
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        validacoesUsuario.validaExclusaoUsuario(id);
        usuario.setUsuarioAtivo(false);
    }

    @Transactional
    public Usuario editarUsuario(AtualizaUsuarioDTO dto) {
        Usuario usuario = repositorio.findById(dto.id())
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        validacoesUsuario.validaEdicaoUsuario(dto);
        usuario.setNome(dto.nome());
        return usuario;

    }


}
