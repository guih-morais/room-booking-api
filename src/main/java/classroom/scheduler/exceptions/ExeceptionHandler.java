package classroom.scheduler.exceptions;

import classroom.scheduler.exceptions.ValidacaoException;
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
                .status(HttpStatus.OK)
                .body("Favor verifique os campos, elemento não foi encontrado no banco de dados.");
    }
}
