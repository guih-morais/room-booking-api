package classroom.scheduler.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExeceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> ValidacaoGlobalHandler(ValidacaoException ex) {
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> ElementoNaoEncontradoGlobalHandler(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(UsuarioNaoLocalizadoException.class)
    public ResponseEntity<String> exceptionUsuarioNaoEncontrado(UsuarioNaoLocalizadoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(SalaNaoLocalizadaException.class)
    public ResponseEntity<String> exceptionSalaNaoEncontrada(SalaNaoLocalizadaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(ReservaNaoLocalizadaException.class)
    public ResponseEntity<String> exceptionReservaNaoEncontrada(ReservaNaoLocalizadaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDTO>> exceptionBeanValidation(MethodArgumentNotValidException ex) {
        List<FieldError> fields = ex.getFieldErrors();
        List<ErroDTO> erros = fields.stream().map(ErroDTO::new).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    private record ErroDTO(String campo, String erro) {
        public ErroDTO(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }

    }


}
