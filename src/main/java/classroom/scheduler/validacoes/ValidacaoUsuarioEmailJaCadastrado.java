package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.models.Validavel;
import classroom.scheduler.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioEmailJaCadastrado implements Validacao{

    UsuarioRepository repositorio;

    public ValidacaoUsuarioEmailJaCadastrado(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void validar(Validavel obj) {
        Usuario usuario = (Usuario) obj;
        if(repositorio.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ValidacaoException("Este e-mail já está cadastrado no banco de dados.");
        }
    }
}
