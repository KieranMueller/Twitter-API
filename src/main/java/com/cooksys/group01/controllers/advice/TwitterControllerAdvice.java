package com.cooksys.group01.controllers.advice;

import com.cooksys.group01.dtos.ErrorDTO;
import com.cooksys.group01.exceptions.BadRequestException;
import com.cooksys.group01.exceptions.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = {"com.cooksys.group01.controllers"})
@ResponseBody
public class TwitterControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDTO handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
        return new ErrorDTO(badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorDTO handleNotAuthorizedRequestException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
        return new ErrorDTO(notAuthorizedException.getMessage());
    }

}
