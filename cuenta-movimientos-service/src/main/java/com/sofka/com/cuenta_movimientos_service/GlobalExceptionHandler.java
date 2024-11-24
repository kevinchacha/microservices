package com.sofka.com.cuenta_movimientos_service;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.utils.CommonUtils;
import com.sofka.com.cuenta_movimientos_service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private CommonUtils utils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        response.put("errors", fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ManageResponse> handleNoSuchElement(NoSuchElementException ex) {
        return new ResponseEntity<>(utils.createResponse(Constants.NOT_FOUND, ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ManageResponse> handleDataIntegrityException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(utils.createResponse(Constants.BAD_REQUEST, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ManageResponse> handleInvalidTypeException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(utils.createResponse(Constants.BAD_REQUEST, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
