package com.Trimple.Cars.exception;

public class CarOwnerNotFoundException extends RuntimeException {

	// Constructor that accepts a message
    public CarOwnerNotFoundException(String message) {
        super(message); // Pass the message to the parent (RuntimeException) constructor
    }

    // Constructor that accepts both a message and a cause (another Throwable)
    public CarOwnerNotFoundException(String message, Throwable cause) {
        super(message, cause); // Pass both the message and cause to the parent constructor
    }

}
