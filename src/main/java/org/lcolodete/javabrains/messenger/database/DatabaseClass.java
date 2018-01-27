package org.lcolodete.javabrains.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.lcolodete.javabrains.messenger.model.Message;
import org.lcolodete.javabrains.messenger.model.Profile;

public class DatabaseClass {

	//NOTE: Not thread-safe!
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
