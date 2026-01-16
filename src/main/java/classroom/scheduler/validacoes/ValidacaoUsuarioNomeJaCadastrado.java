package classroom.scheduler.validacoes;

import classroom.scheduler.exceptions.ValidacaoException;
import classroom.scheduler.models.Usuario;
import classroom.scheduler.models.Validavel;
import classroom.scheduler.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioNomeJaCadastrado implements Validacao{

    private final UsuarioRepository repositorio;

    public ValidacaoUsuarioNomeJaCadastrado(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void validar(Validavel obj) {
        Usuario usuario = (Usuario) obj;
        String nomeUsuario = usuario.getNome();
        if(repositorio.findByNome(nomeUsuario).isPresent()) {
            throw new ValidacaoException("Este usuário já está cadastrado no banco de dados.");
        }
    }
}
