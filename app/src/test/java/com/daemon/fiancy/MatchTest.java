package com.daemon.fiancy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daemon.fiancy.models.Advertisements;
import com.daemon.fiancy.models.MatchLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MatchTest {

    private MatchLogic matchLogic;

    @BeforeEach
    public void setUp() {
        matchLogic = new MatchLogic();
    }

    @Test
    public void testMatchCalculationWithoutAnyDetail() {
        Advertisements advertisement = new Advertisements();
        matchLogic.setAdvertisementUser(advertisement);
        matchLogic.setAdvertisementOther(advertisement);

        double result = matchLogic.matchCalculation();
        assertEquals(0.0, result);
    }

    @Test
    public void testMatchCalculationWithAdDetail() {
        Advertisements advertisement1 = new Advertisements();
        Advertisements advertisement2 = new Advertisements();

        advertisement1.setAge("25");
        advertisement1.setGender("Female");
        advertisement1.setStatus("Never Married");
        advertisement1.setProfession("Engineer");
        advertisement1.setReligion("Buddhist");
        advertisement1.setMinEducatuinLevel("Post Graduate Diploma");

        ArrayList<String> hobbieList1 = new ArrayList<>();
        hobbieList1.add("Reading");
        hobbieList1.add("Shopping");
        hobbieList1.add("Dancing");
        advertisement1.setHobbiesList(hobbieList1);

        advertisement2.setAge("30");
        advertisement2.setGender("Male");
        advertisement2.setStatus("Never Married");
        advertisement2.setProfession("Software Engineer");
        advertisement2.setReligion("Buddhist");
        advertisement2.setMinEducatuinLevel("Bachelor's Degree or Equivalent");

        ArrayList<String> hobbieList2 = new ArrayList<>();
        hobbieList2.add("Music");
        hobbieList2.add("Reading");
        hobbieList2.add("Video Games");
        advertisement2.setHobbiesList(hobbieList2);

        matchLogic.setAdvertisementUser(advertisement1);
        matchLogic.setAdvertisementOther(advertisement2);
        double result = matchLogic.matchCalculation();

        assertEquals(82.55, result);
    }
}
