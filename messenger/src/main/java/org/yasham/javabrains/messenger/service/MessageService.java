package org.yasham.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.yasham.javabrains.messenger.database.DatabaseClass;
import org.yasham.javabrains.messenger.exception.DataNotFoundException;
import org.yasham.javabrains.messenger.message.Message;

public class MessageService {

	private static Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1, "Hello World!", "yasham"));
		messages.put(2L, new Message(2, "Hello Jersey!", "yasham"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messageForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messageForYear.add(message);
			}
		}
		return messageForYear;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<>(messages.values());
		if (start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);
	}

	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null)
			throw new DataNotFoundException("Message with id " + id + "does not exist.");
		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0)
			return null;
		messages.put(message.getId(), message);
		return message;
	}

	public void removeMessage(long id) {
		messages.remove(id);
	}

}
