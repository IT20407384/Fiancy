package com.daemon.fiancy;

import com.daemon.fiancy.models.RejectedAds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReasonTest {
    private RejectedAds rejectedAds;

    @BeforeEach
    public void setUp() {
        rejectedAds = new RejectedAds();
    }

    @Test
    public void testMatchCalculation() {
        int x=0;
        boolean result = rejectedAds.ReasonNull("Chamodi");
        if(result){
            x=1;
        }
        assertEquals(1,x);
    }

}
