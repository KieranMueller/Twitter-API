package com.cooksys.group01.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {
	
	
	@Serial
	private static final long serialVersionUID = 3848857929184917363L;
	
	private String message;

}
