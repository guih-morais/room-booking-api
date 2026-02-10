package com.guih.morais.room.booking.service;

import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
import com.guih.morais.room.booking.exceptions.UsuarioNaoLocalizadoException;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.repository.UsuarioRepository;
import com.guih.morais.room.booking.validacoes.ValidacoesUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    UsuarioService service;
    @Mock
    UsuarioRepository repositorio;

    Usuario usuario;

    @Mock
    ValidacoesUsuario validacoesUsuario;

    @Mock
    InputUsuarioDTO inputDTO;

    @Captor
    ArgumentCaptor<Usuario> captor;

    AtualizaUsuarioDTO dto;

    @Test
    void deveriaPermitirEdicaoUsuario() {
        this.usuario = new Usuario(new InputUsuarioDTO("João", "joao@email.com"));
        this.dto = new AtualizaUsuarioDTO(1L, "Guilherme");
        BDDMockito.given(repositorio.findById(dto.id()))
                .willReturn(Optional.of(usuario));

        service.editarUsuario(dto);

        BDDMockito.then(validacoesUsuario)
                .should().validaEdicaoUsuario(dto);

        Assertions.assertEquals("Guilherme", usuario.getNome());
    }

    @Test
    void deveriaLancarExceptionUsuarioNaoLocalizado() {
        this.usuario = new Usuario(new InputUsuarioDTO("João", "joao@email.com"));
        this.dto = new AtualizaUsuarioDTO(1L, "Guilherme");
        BDDMockito.given(repositorio.findById(dto.id()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoLocalizadoException.class, () -> service.editarUsuario(dto));
    }

    @Test
    void deveriaPermitirCriacaoUsuario() {
        InputUsuarioDTO dadosUsuario = new InputUsuarioDTO("João", "joao@email.com");

        service.criarUsuario(dadosUsuario);

        BDDMockito.then(validacoesUsuario).should().validaCriacaoUsuario(dadosUsuario);
        BDDMockito.then(repositorio).should().save(captor.capture());
        Usuario usuarioCapturado = captor.getValue();
        Assertions.assertEquals("João", usuarioCapturado.getNome());
        Assertions.assertEquals("joao@email.com", usuarioCapturado.getEmail());
    }



}



