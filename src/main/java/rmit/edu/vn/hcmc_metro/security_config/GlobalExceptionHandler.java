package rmit.edu.vn.hcmc_metro.security_config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

    String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getObjectName() + "." + error.getField() + ": " + error.getDefaultMessage())
            .reduce((error1, error2) -> error1 + ",\n" + error2)
            .orElse("Validation failed");

    System.err.println("Error in " + ex.getTarget());
    System.err.println(errorMessage);
    
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}