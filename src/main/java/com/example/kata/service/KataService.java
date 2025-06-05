package com.example.kata.service;

import org.springframework.stereotype.Service;

@Service
public class KataService {

    public String transform(int number) {

        if (number < 0 || number > 100){
            throw new IllegalArgumentException("the number should be between 0 and 100");
        }

        StringBuilder result = new StringBuilder();

        if (number % 3 == 0) {
            result.append("FOO");
        }
        if (number % 5 == 0) {
            result.append("BAR");
        }

        char[] figures = String.valueOf(number).toCharArray();
        for (char digit : figures) {
            if (digit == '3') {
                result.append("FOO");
            } else if (digit == '5') {
                result.append("BAR");
            } else if (digit == '7') {
                result.append("QUIX");
            }
        }

        return !result.isEmpty() ? result.toString() : String.valueOf(number);
    }
}
