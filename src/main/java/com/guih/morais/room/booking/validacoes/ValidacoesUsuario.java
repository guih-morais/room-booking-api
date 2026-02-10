package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
import com.guih.morais.room.booking.exceptions.MensagemErro;
import com.guih.morais.room.booking.exceptions.UsuarioNaoLocalizadoException;
import com.guih.morais.room.booking.exceptions.ValidacaoException;
import com.guih.morais.room.booking.models.Usuario;
import com.guih.morais.room.booking.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacoesUsuario {

    private final UsuarioRepository usuarioRepository;


    public ValidacoesUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validaCriacaoUsuario(InputUsuarioDTO dto) {
        validaNomeUsuarioJaExistente(dto.nome());
        validaEmailJaExistente(dto.email());
    }

    public void validaEdicaoUsuario(AtualizaUsuarioDTO dto) {
        validaNomeUsuarioJaExistente(dto.nome());
        validaUsuarioAtivo(dto.id());
        validaUsuarioPossuiReservaAtivaOuEmAndamento(dto.id());
    }

    public void validaExclusaoUsuario(Long id) {
        validaUsuarioAtivo(id);
        validaUsuarioPossuiReservaAtivaOuEmAndamento(id);
    }


    private void validaNomeUsuarioJaExistente(String nome) {
        if (usuarioRepository.findByNome(nome).isPresent()) {
            throw new ValidacaoException(MensagemErro.USUARIO_NOME_USUARIO_JA_CADASTRADO);
        }
    }
    private void validaEmailJaExistente(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new ValidacaoException(MensagemErro.USUARIO_EMAIL_JA_CADASTRADO);
        }
    }

    public void validaUsuarioAtivo(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        if(!usuario.isUsuarioAtivo()) {
            throw new ValidacaoException(MensagemErro.USUARIO_INATIVO);
        }
    }

    private void validaUsuarioPossuiReservaAtivaOuEmAndamento(Long id) {
        if (usuarioRepository.usuarioPossuiReservaAtivaOuEmAndamento(id)) {
            throw new ValidacaoException(MensagemErro.USUARIO_ESTA_EM_UMA_RESERVA_ATIVA_OU_EM_ANDAMENTO);
        }
    }


}
