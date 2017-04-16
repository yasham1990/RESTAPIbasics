package org.yasham.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yasham.javabrains.messenger.database.DatabaseClass;
import org.yasham.javabrains.messenger.message.Profile;

public class ProfileSevice {
	private static Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public ProfileSevice() {
		profiles.put("yasham",new Profile(1L,"yasham","Yasham","Singhal"));
		//messages.put(2L,new Message(2,"Hello Jersey!","yasham"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty())
			return null;
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public void removeProfile(String profileName) {
		profiles.remove(profileName);
	}
}
