package classroom.scheduler.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExeceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> exceptionGlobalHandler(ValidacaoException ex) {
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> exceptionGlobalHandler2(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> exceptionGlobalHandler3(ObjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Favor verifique os campos, elemento não foi encontrado no banco de dados.");
    }

    @ExceptionHandler(UsuarioNaoLocalizadoException.class)
    public ResponseEntity<String> exceptionUsuarioNaoEncontrado(UsuarioNaoLocalizadoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(SalaNaoLocalizadaException.class)
    public ResponseEntity<String> exceptionSalaNaoEncontrada(SalaNaoLocalizadaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(ReservaNaoLocalizadaException.class)
    public ResponseEntity<String> exceptionReservaNaoEncontrada(ReservaNaoLocalizadaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
