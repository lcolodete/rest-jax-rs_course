package org.lcolodete.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.lcolodete.javabrains.messenger.database.DatabaseClass;
import org.lcolodete.javabrains.messenger.exception.DataNotFoundException;
import org.lcolodete.javabrains.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		this.messages.put(1L, new Message(1L, "Hello World!", "lcolodete"));
		this.messages.put(2L, new Message(2L, "Hello Jersey!", "lcolodete"));
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(this.messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : this.messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> list = new ArrayList<Message>(this.messages.values());
		if (start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);
	}
	
	public Message getMessage(long id) {
		Message message = this.messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id "+id+" not found."); 
		}
		return message;
	}
	
	public Message addMessage(Message m) {
		m.setId(this.messages.size() + 1);
		this.messages.put(m.getId(), m);
		return m;
	}
	
	public Message updateMessage(Message m) {
		if (m.getId() <= 0) {
			return null;
		}
		this.messages.put(m.getId(), m);
		return m;
	}
	
	public Message removeMessage(long id) {
		return this.messages.remove(id);
	}
}
