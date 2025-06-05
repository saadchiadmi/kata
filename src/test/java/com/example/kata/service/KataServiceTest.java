package com.example.kata.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class KataServiceTest {

    private final KataService service = new KataService();

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, FOOFOO",
            "4, 4",
            "5, BARBAR",
            "6, FOO",
            "7, QUIX",
            "8, 8",
            "9, FOO",
            "15, FOOBARBAR",
            "33, FOOFOOFOO",
            "51, FOOBAR",
            "53, BARFOO"
    })
    void shouldTransformNumbersCorrectly(int input, String expected) {
        assertEquals(expected, service.transform(input));
    }



    @Test
    void shouldThrowExceptionForNumberBelowZero() {
        assertThrows(IllegalArgumentException.class, () -> service.transform(-1));
    }

    @Test
    void shouldThrowExceptionForNumberAboveHundred() {
        assertThrows(IllegalArgumentException.class, () -> service.transform(101));
    }
}