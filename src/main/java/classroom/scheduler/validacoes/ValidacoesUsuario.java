package classroom.scheduler.validacoes;

import classroom.scheduler.dto.input.InputUsuarioDTO;
import classroom.scheduler.dto.updates.AtualizaUsuarioDTO;
import classroom.scheduler.exceptions.UsuarioNaoLocalizadoException;
import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Sala;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.repository.SalaRepository;
import classroom.scheduler.repository.UsuarioRepository;
import io.micrometer.common.util.StringUtils;
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


}
