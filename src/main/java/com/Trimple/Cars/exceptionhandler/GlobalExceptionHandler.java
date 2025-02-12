package com.Trimple.Cars.exceptionhandler;

import com.Trimple.Cars.dto.CommonErrorResponse;
import com.Trimple.Cars.exception.CarNotFoundException;
import com.Trimple.Cars.exception.LeaseNotFoundException;
import com.Trimple.Cars.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;


import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle CarNotFoundException
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<CommonErrorResponse> handleCarNotFoundException(CarNotFoundException ex) {
        logger.error("Car not found: {}", ex.getMessage());
        CommonErrorResponse errorResponse = new CommonErrorResponse("CAR_NOT_FOUND", "Car Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle LeaseNotFoundException
    @ExceptionHandler(LeaseNotFoundException.class)
    public ResponseEntity<CommonErrorResponse> handleLeaseNotFoundException(LeaseNotFoundException ex) {
        logger.error("Lease not found: {}", ex.getMessage());
        CommonErrorResponse errorResponse = new CommonErrorResponse("LEASE_NOT_FOUND", "Lease Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle EntityNotFoundException (generic for other entities)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found: {}", ex.getMessage());
        CommonErrorResponse errorResponse = new CommonErrorResponse("ENTITY_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle Invalid Data Exception


    // Handle DataIntegrityViolationException (for database constraints)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation: {}", ex.getMessage());
        CommonErrorResponse errorResponse = new CommonErrorResponse("DATA_INTEGRITY_VIOLATION", "Data Integrity Violation");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle general exception (catch-all for other exceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorResponse> handleAllExceptions(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage());
        CommonErrorResponse errorResponse = new CommonErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        logger.error("Unauthorized access: {}", ex.getMessage());

        CommonErrorResponse errorResponse = new CommonErrorResponse("UNAUTHORIZED_ACCESS", // error code
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Validation error: {}", ex.getMessage());

        // Collect all constraint violation messages
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        CommonErrorResponse errorResponse = new CommonErrorResponse(
                "VALIDATION_ERROR",    // error code
                errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("Validation failed: {}", ex.getMessage());

        // Collect all field validation errors
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        CommonErrorResponse errorResponse = new CommonErrorResponse(
                "VALIDATION_ERROR",         // error code
                String.join(", ", errorMessages));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request

    }
}
