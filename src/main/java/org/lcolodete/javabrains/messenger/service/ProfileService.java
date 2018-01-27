package org.lcolodete.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lcolodete.javabrains.messenger.database.DatabaseClass;
import org.lcolodete.javabrains.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		this.profiles.put("lcolodete", new Profile(1L, "lcolodete", "Leandro", "Colodete"));
	}
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(this.profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return this.profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(this.profiles.size() + 1);
		this.profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		this.profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return this.profiles.remove(profileName);
	}
}
