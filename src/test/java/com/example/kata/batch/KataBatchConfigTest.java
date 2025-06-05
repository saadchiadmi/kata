package com.example.kata.batch;

import com.example.kata.service.KataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ItemProcessor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KataBatchConfigTest {

    @InjectMocks
    private KataBatchConfig batchConfig;

    @Mock
    private KataService kataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFooBarQuixItemProcessor() throws Exception {
        when(kataService.transform(3)).thenReturn("FOO");

        ItemProcessor<Integer, String> processor = batchConfig.kataItemProcessor();
        String result = processor.process(3);
        assertEquals("3 \"FOO\"", result);
    }

}
