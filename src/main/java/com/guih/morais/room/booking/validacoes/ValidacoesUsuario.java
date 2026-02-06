package com.guih.morais.room.booking.validacoes;

import com.guih.morais.room.booking.dto.input.InputUsuarioDTO;
import com.guih.morais.room.booking.dto.updates.AtualizaUsuarioDTO;
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
        validaUsuarioAtivo(dto.id());
        validaNomeUsuarioJaExistente(dto.nome());
        validaUsuarioPossuiReservaAtivaOuEmAndamento(dto.id());
    }

    public void validaExclusaoUsuario(Long id) {
        validaUsuarioAtivo(id);
        validaUsuarioPossuiReservaAtivaOuEmAndamento(id);
    }


    private void validaNomeUsuarioJaExistente(String nome) {
        if (usuarioRepository.findByNome(nome).isPresent()) {
            throw new ValidacaoException("Nome de usuário já cadastrado no banco de dados.");
        }
    }
    private void validaEmailJaExistente(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new ValidacaoException("Email já cadastrado no banco de dados.");
        }
    }

    public void validaUsuarioAtivo(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        if(!usuario.isUsuarioAtivo()) {
            throw new ValidacaoException("Usuário está desativado no banco de dados.");
        }
    }

    private void validaUsuarioPossuiReservaAtivaOuEmAndamento(Long id) {
        if (usuarioRepository.usuarioPossuiReservaAtivaOuEmAndamento(id)) {
            throw new ValidacaoException("Não é possível desativar ou editar um usuário que possui uma Reserva Ativa ou Em Andamento");
        }
    }


}
