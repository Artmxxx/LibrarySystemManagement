package com.example.OODevProject.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MyDataTest {

    private MyData myData;

    @BeforeEach
    public void init() {
        // Initialize MyData with a value of 0 before each test
        myData = new MyData(0);
    }

    @Test
    public void testGetI() {
        // Verify that the getter returns the initialized value
        assertEquals(0, myData.getI(), "The value of i should be 0");
    }
}
