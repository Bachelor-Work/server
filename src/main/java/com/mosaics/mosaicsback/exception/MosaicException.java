package com.mosaics.mosaicsback.exception;

import org.springframework.http.HttpStatus;

public class MosaicException extends RuntimeException {

    private final HttpStatus code;

    public MosaicException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}