package com.cooksys.group01.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2026814081624680271L;

    private String message;
}
