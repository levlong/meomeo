package com.test.demo.exception;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(Long id) {
        super("Appointment Not Found " + id);
    }

}
