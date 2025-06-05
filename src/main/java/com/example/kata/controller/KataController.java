package com.example.kata.controller;

import com.example.kata.service.KataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KataController {

    private final KataService kataService;

    @Autowired
    public KataController(KataService kataService) {
        this.kataService = kataService;
    }

    @GetMapping("/transform/{number}")
    public String transform(@PathVariable int number) {
        return kataService.transform(number);
    }
}
