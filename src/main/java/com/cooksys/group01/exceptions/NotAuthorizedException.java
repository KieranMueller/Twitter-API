package com.cooksys.group01.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 6866344519140233671L;

    private String message;
}
