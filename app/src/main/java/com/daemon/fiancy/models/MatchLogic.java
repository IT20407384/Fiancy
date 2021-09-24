package com.daemon.fiancy.models;

import java.util.ArrayList;

public class MatchLogic {
    private Advertisements advertisementUser;
    private Advertisements advertisementOther;

    public MatchLogic() { }

    public Advertisements getAdvertisementUser() {
        return advertisementUser;
    }

    public void setAdvertisementUser(Advertisements advertisementUser) {
        this.advertisementUser = advertisementUser;
    }

    public Advertisements getAdvertisementOther() {
        return advertisementOther;
    }

    public void setAdvertisementOther(Advertisements advertisementOther) {
        this.advertisementOther = advertisementOther;
    }

    public double matchCalculation() {
        double matchPercentage = 0;

        boolean genderUser = false;     //male = true, female = false
        boolean genderOther = false;
        int ageGroupUser = 0, ageGroupOther = 0;    //18 - 30 = 1       //45 - 60 = 3
                                            //30 - 45 = 2       //60 > x = 4

        int religionUser = 0, religionOther = 0;    //Buddhist = 1      //Catholic = 2
                                            //Cristian = 3      //Islam = 4         //Hinduism = 5

        int maxEduLevelUser, maxEduLevelOther;      // O level = 1      // A level = 2
                                                    //Diploma = 3       //Prof. Qualify. = 4
                                                    //  UG = 5          // Batchelor = 6
                                                    // Post grad. = 7   // Masters = 8      //pHd = 9

        boolean [] hobbiesUser = new boolean[12];
        for(int i=0; i<12; i++)
            hobbiesUser[i] = false;

        boolean [] hobbiesOther = new boolean[12];
        for(int i=0; i<12; i++)
            hobbiesOther[i] = false;

        /*
            Reading = 0 th index
            Collection = 1 st index
            Music = 2nd  index
            video games = 3rd index
            fishing = 4 th index
            walking = 5 th index
            travelling = 6 th index
            sports = 7 th index
            eating out = 8 th index
            gardening = 9 th index
            shopping = 10 th index
            dancing = 11 th index
         */

        int statusUser, statusOther;        //married = 1         //widowed = 3
                                            //never married = 2       //divorced = 4        //other = 5

        String professionUser;
        String professionOther;
        ArrayList<String> hobbiesUserFromDB;
        ArrayList<String> hobbiesOtherFromDB;

        genderUser = (advertisementUser.getGender().equalsIgnoreCase("Male"));
        genderOther = (advertisementOther.getGender().equalsIgnoreCase("Male"));

        if(Integer.parseInt(advertisementUser.getAge()) >= 18 && Integer.parseInt(advertisementUser.getAge()) <= 30)
            ageGroupUser = 1;
        else if(Integer.parseInt(advertisementUser.getAge()) >= 31 && Integer.parseInt(advertisementUser.getAge()) <= 45)
            ageGroupUser = 2;
        else if(Integer.parseInt(advertisementUser.getAge()) >= 46 && Integer.parseInt(advertisementUser.getAge()) <= 60)
            ageGroupUser = 3;
        else if(Integer.parseInt(advertisementUser.getAge()) >= 61 )
            ageGroupUser = 4;

        if(Integer.parseInt(advertisementOther.getAge()) >= 18 && Integer.parseInt(advertisementOther.getAge()) <= 30)
            ageGroupOther = 1;
        else if(Integer.parseInt(advertisementOther.getAge()) >= 31 && Integer.parseInt(advertisementOther.getAge()) <= 45)
            ageGroupOther = 2;
        else if(Integer.parseInt(advertisementOther.getAge()) >= 46 && Integer.parseInt(advertisementOther.getAge()) <= 60)
            ageGroupOther = 3;
        else if(Integer.parseInt(advertisementOther.getAge()) >= 61)
            ageGroupOther = 4;

        if(advertisementUser.getReligion().equalsIgnoreCase("Buddhist"))
            religionUser = 1;
        else if(advertisementUser.getReligion().equalsIgnoreCase("Catholic"))
            religionUser = 2;
        else if(advertisementUser.getReligion().equalsIgnoreCase("Cristian"))
            religionUser = 3;
        else if(advertisementUser.getReligion().equalsIgnoreCase("Islam"))
            religionUser = 4;
        else if(advertisementUser.getReligion().equalsIgnoreCase("Hinduism"))
            religionUser = 5;

        if(advertisementOther.getReligion().equalsIgnoreCase("Buddhist"))
            religionOther = 1;
        else if(advertisementOther.getReligion().equalsIgnoreCase("Catholic"))
            religionOther = 2;
        else if(advertisementOther.getReligion().equalsIgnoreCase("Cristian"))
            religionOther = 3;
        else if(advertisementOther.getReligion().equalsIgnoreCase("Islam"))
            religionOther = 4;
        else if(advertisementOther.getReligion().equalsIgnoreCase("Hinduism"))
            religionOther = 5;

        if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Up to GCE O/L"))
            maxEduLevelUser = 1;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Up to GCE A/L"))
            maxEduLevelUser = 2;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Diploma"))
            maxEduLevelUser = 3;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Professional Qualification"))
            maxEduLevelUser = 4;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Undergraduate"))
            maxEduLevelUser = 5;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Bachelor's Degree or Equivalent"))
            maxEduLevelUser = 6;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Post Graduate Diploma"))
            maxEduLevelUser = 7;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Master's Degree or Equivalent"))
            maxEduLevelUser = 8;
        else if(advertisementUser.getMinEducatuinLevel().equalsIgnoreCase("Phd or Post Doctoral"))
            maxEduLevelUser = 9;

        if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Up to GCE O/L"))
            maxEduLevelOther = 1;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Up to GCE A/L"))
            maxEduLevelOther = 2;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Diploma"))
            maxEduLevelOther = 3;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Professional Qualification"))
            maxEduLevelOther = 4;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Undergraduate"))
            maxEduLevelOther = 5;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Bachelor's Degree or Equivalent"))
            maxEduLevelOther = 6;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Post Graduate Diploma"))
            maxEduLevelOther = 7;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Master's Degree or Equivalent"))
            maxEduLevelOther = 8;
        else if(advertisementOther.getMinEducatuinLevel().equalsIgnoreCase("Phd or Post Doctoral"))
            maxEduLevelOther = 9;

        if(advertisementUser.getStatus().equalsIgnoreCase("Married"))
            statusUser = 1;
        else if(advertisementUser.getStatus().equalsIgnoreCase("Never Married"))
            statusUser = 2;
        else if(advertisementUser.getStatus().equalsIgnoreCase("Widowed"))
            statusUser = 3;
        else if(advertisementUser.getStatus().equalsIgnoreCase("Devorced"))
            statusUser = 4;
        else if(advertisementUser.getStatus().equalsIgnoreCase("Other"))
            statusUser = 5;

        if(advertisementOther.getStatus().equalsIgnoreCase("Married"))
            statusOther = 1;
        else if(advertisementOther.getStatus().equalsIgnoreCase("Never Married"))
            statusOther = 2;
        else if(advertisementOther.getStatus().equalsIgnoreCase("Widowed"))
            statusOther = 3;
        else if(advertisementOther.getStatus().equalsIgnoreCase("Devorced"))
            statusOther = 4;
        else if(advertisementOther.getStatus().equalsIgnoreCase("Other"))
            statusOther = 5;

        professionUser = advertisementUser.getProfession();
        professionOther = advertisementOther.getProfession();


        // Setting up the boolean arrays with corresponding hobbies
        hobbiesUserFromDB = advertisementUser.getHobbiesList();
        hobbiesOtherFromDB = advertisementOther.getHobbiesList();

        for(String localHobbie : hobbiesUserFromDB) {
            if(localHobbie.equalsIgnoreCase("Reading"))
                hobbiesUser[0] = true;
            else if(localHobbie.equalsIgnoreCase("Collecting"))
                hobbiesUser[1] = true;
            else if(localHobbie.equalsIgnoreCase("Music"))
                hobbiesUser[2] = true;
            else if(localHobbie.equalsIgnoreCase("Video Games"))
                hobbiesUser[3] = true;
            else if(localHobbie.equalsIgnoreCase("Fishing"))
                hobbiesUser[4] = true;
            else if(localHobbie.equalsIgnoreCase("Walking"))
                hobbiesUser[5] = true;
            else if(localHobbie.equalsIgnoreCase("Traveling"))
                hobbiesUser[6] = true;
            else if(localHobbie.equalsIgnoreCase("Watching Sports"))
                hobbiesUser[7] = true;
            else if(localHobbie.equalsIgnoreCase("Eating Out"))
                hobbiesUser[8] = true;
            else if(localHobbie.equalsIgnoreCase("Gardening"))
                hobbiesUser[9] = true;
            else if(localHobbie.equalsIgnoreCase("Shopping"))
                hobbiesUser[10] = true;
            else if(localHobbie.equalsIgnoreCase("Dancing"))
                hobbiesUser[11] = true;
        }

        for(String localHobbie : hobbiesOtherFromDB) {
            if(localHobbie.equalsIgnoreCase("Reading"))
                hobbiesOther[0] = true;
            else if(localHobbie.equalsIgnoreCase("Collecting"))
                hobbiesOther[1] = true;
            else if(localHobbie.equalsIgnoreCase("Music"))
                hobbiesOther[2] = true;
            else if(localHobbie.equalsIgnoreCase("Video Games"))
                hobbiesOther[3] = true;
            else if(localHobbie.equalsIgnoreCase("Fishing"))
                hobbiesOther[4] = true;
            else if(localHobbie.equalsIgnoreCase("Walking"))
                hobbiesOther[5] = true;
            else if(localHobbie.equalsIgnoreCase("Traveling"))
                hobbiesOther[6] = true;
            else if(localHobbie.equalsIgnoreCase("Watching Sports"))
                hobbiesOther[7] = true;
            else if(localHobbie.equalsIgnoreCase("Eating Out"))
                hobbiesOther[8] = true;
            else if(localHobbie.equalsIgnoreCase("Gardening"))
                hobbiesOther[9] = true;
            else if(localHobbie.equalsIgnoreCase("Shopping"))
                hobbiesOther[10] = true;
            else if(localHobbie.equalsIgnoreCase("Dancing"))
                hobbiesOther[11] = true;
        }

        if(genderUser != genderOther)
            matchPercentage += 40.0;

        if(religionUser == religionOther)
            matchPercentage += 25.0;

//        if(genderUser)
//            int ageDiff = ()

        return matchPercentage;
    }
}
