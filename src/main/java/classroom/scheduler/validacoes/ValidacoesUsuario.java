package classroom.scheduler.validacoes;

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

    public static void validaNomeUsuarioJaExistente(String nome, UsuarioRepository usuarioRepository) {
        if (usuarioRepository.findByNome(nome).isPresent()) {
            throw new ValidacaoException("Nome de usuário já cadastrado no banco de dados.");
        }
    }
    public static void validaEmailJaExistente(String email, UsuarioRepository usuarioRepository) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new ValidacaoException("Email já cadastrado no banco de dados.");
        }
    }

    public static void validaUsuarioAtivo(Long id, UsuarioRepository usuarioRepository) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoLocalizadoException::new);
        if(!usuario.isUsuarioAtivo()) {
            throw new ValidacaoException("Usuário está desativado no banco de dados.");
        }
    }


}
