package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.mappers.HashtagMapper;
import com.cooksys.group01.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean doesHashtagExist(@PathVariable String label) {
        return validateService.doesHashtagExist(label);
    }

    // Just created this endpoint to test something, going to leave it up for now - Kieran
    @GetMapping
    public List<HashtagDTO> testGetAll() {
        return validateService.getAllHashtags();
    }
}
