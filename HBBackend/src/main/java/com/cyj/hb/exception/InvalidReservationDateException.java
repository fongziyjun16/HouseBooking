package com.cyj.hb.exception;

public class InvalidReservationDateException extends RuntimeException {
    public InvalidReservationDateException(String message) {
        super(message);
    }
}
