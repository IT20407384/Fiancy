package com.daemon.fiancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daemon.fiancy.models.ReportedADModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportAdTest {
    private ReportAd reportAd;
    private ReportedADModel reportedADModel;

    @BeforeEach
    public void setUp() {
        reportAd = new ReportAd();
        reportedADModel = new ReportedADModel("100", "nikela@gmail.com", "test message", "test reason");
    }

    @Test
    public void testSetDetailstomodel() {
        Boolean result = reportAd.InsertReportAd(reportedADModel);
        assertEquals(true, result);
    }
}
