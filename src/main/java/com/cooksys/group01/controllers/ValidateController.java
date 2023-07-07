package com.cooksys.group01.controllers;

import com.cooksys.group01.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("tag/exists/{label}")
    public boolean doesHashtagExist(@PathVariable String label) {
        return validateService.doesHashtagExist(label);
    }
    
    @GetMapping("username/exists/@{username}")
    public boolean doesUserExist(@PathVariable String username) {
    	return validateService.doesUserExist(username);
    }

    @GetMapping("username/available/@{username}")
    public boolean isUsernameAvailable(@PathVariable String username) {
        return validateService.isUsernameAvailable(username);
    }
}
