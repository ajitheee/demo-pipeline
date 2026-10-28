package com.ajith.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakTest {

    @Test
    void additionWorks() {
        assertEquals(4, 2 + 1, "Intentional failure to test the pipeline");
    }
}