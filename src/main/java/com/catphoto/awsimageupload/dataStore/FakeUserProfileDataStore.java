package com.catphoto.awsimageupload.dataStore;

import com.catphoto.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    //a list of user
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    //add some user profile
    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("0b68038e-a2ac-4976-9753-4f4c87abdc57"),"Cat of the Day", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("d55d8979-1970-4112-b38d-06a1633ae618"), "Seal of the Day", null));

    }

    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }

}
