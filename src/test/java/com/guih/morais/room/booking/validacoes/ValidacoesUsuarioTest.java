package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
import com.guih.morais.room.booking.exceptions.UsuarioNaoLocalizadoException;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ValidacoesUsuarioTest {

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    InputUsuarioDTO inputDTO;
    @Mock
    AtualizaUsuarioDTO atualizaDTO;
    @Mock
    Usuario usuario;
    @InjectMocks
    ValidacoesUsuario validacoesUsuario;

    @Test
    void deveriaPermitirCriacaoUsuario() {
        BDDMockito.given(usuarioRepository.findByNome(inputDTO.nome())).willReturn(Optional.empty());
        BDDMockito.given(usuarioRepository.findByEmail(inputDTO.email())).willReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> validacoesUsuario.validaCriacaoUsuario(inputDTO));
    }

    @Test
    void deveriaLancarExceptionNomeUsuarioJaCadastrado() {
        BDDMockito.given(usuarioRepository.findByNome(inputDTO.nome())).willReturn(Optional.of(usuario));
        Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesUsuario.validaCriacaoUsuario(inputDTO));
    }

    @Test
    void deveriaLancarExceptionEmailUsuarioJaCadastrado() {
        BDDMockito.given(usuarioRepository.findByEmail(inputDTO.email())).willReturn(Optional.of(usuario));
        Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesUsuario.validaCriacaoUsuario(inputDTO));
    }

    @Test
    void deveriaPermitirEdicaoUsuario() {

        BDDMockito.given(usuarioRepository.findById(atualizaDTO.id())).willReturn(Optional.of(usuario));
        BDDMockito.given(usuario.isUsuarioAtivo()).willReturn(true);
        BDDMockito.given(usuarioRepository.usuarioPossuiReservaAtivaOuEmAndamento(atualizaDTO.id())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacoesUsuario.validaEdicaoUsuario(atualizaDTO));
    }

    @Test
    void deveriaLancarUsuarioNaoEncontradoException() {
        BDDMockito.given(usuarioRepository.findById(atualizaDTO.id())).willReturn(Optional.empty());
        Assertions.assertThrows(UsuarioNaoLocalizadoException.class,
                () -> validacoesUsuario.validaEdicaoUsuario(atualizaDTO));
    }

    @Test
    void deveriaLancarValidacaoExceptionUsuarioNaoAtivoNoBancoDeDados() {

        BDDMockito.given(usuarioRepository.findById(atualizaDTO.id())).willReturn(Optional.of(usuario));
        BDDMockito.given(usuario.isUsuarioAtivo()).willReturn(false);
        Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesUsuario.validaEdicaoUsuario(atualizaDTO),
                "Usuário está desativado no banco de dados.");
    }

    @Test
    void deveriaLancarValidacaoExceptionUsuarioPossuiReservaAtivaOuEmAndamento() {

        BDDMockito.given(usuarioRepository.findById(atualizaDTO.id())).willReturn(Optional.of(usuario));
        BDDMockito.given(usuario.isUsuarioAtivo()).willReturn(true);
        BDDMockito.given(usuarioRepository.usuarioPossuiReservaAtivaOuEmAndamento(atualizaDTO.id())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class,
                () -> validacoesUsuario.validaEdicaoUsuario(atualizaDTO));

    }
    }

