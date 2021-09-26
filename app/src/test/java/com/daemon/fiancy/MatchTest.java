package com.daemon.fiancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daemon.fiancy.models.MatchLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchTest {

    private MatchLogic matchLogic;

    @BeforeEach
    public void setUp() {
        matchLogic = new MatchLogic();
    }

    @Test
    public void testMatchCalculation() {
        int result = matchLogic.add(2,3);
        assertEquals(5, result);
    }
}
