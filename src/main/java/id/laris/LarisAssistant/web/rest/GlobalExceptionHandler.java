package id.laris.LarisAssistant.web.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.OK).body("An error occurred " + e.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException e) {
        String errMsg;
        HttpStatusCode statusCode = e.getStatusCode();
        if (statusCode.equals(UNAUTHORIZED)) {
            errMsg = "Authentication error: Please check your API key.";
        } else if (statusCode.equals(TOO_MANY_REQUESTS)) {
            errMsg = "Rate limit exceeded: Please slow down your requests.";
        } else {
            errMsg = "Error occurred: " + e.getResponseBodyAsString();
        }
        log.error("Error occurred: {}", errMsg);
        return ResponseEntity.status(HttpStatus.OK).body(errMsg);
    }
}
