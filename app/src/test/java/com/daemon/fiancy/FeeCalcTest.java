package com.daemon.fiancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daemon.fiancy.models.MatchLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeeCalcTest {
    private AdConfirmActivity adConfirmActivity;

    @BeforeEach
    public void setUp() {
        adConfirmActivity = new AdConfirmActivity();
    }

    @Test
    public void testFeeCalculation() {
        double result = adConfirmActivity.calculateAdFee(100);
        assertEquals(4050, result);
    }
}
