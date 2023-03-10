package com.ata.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ata.service.TextService;

public class User extends BaseEntity {
	private String userID;
	private String lastName;
	private String firstName;
	private String userName;
	private String password;
	private String gender;
	private String dateOfBirth;
	private ArrayList<Message> messages;
	private ArrayList<User> receivers;
	private HashMap<User, String> aliases;

	public User(String userID, String lastName, String firstName, String userName, String password, String gender,
			String dateOfBirth) {
		this.userID = userID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		messages = new ArrayList<Message>();
		receivers = new ArrayList<>();
	}

	private String hash(String text) {
		TextService textService = new TextService();
		return textService.hashMD5(text);
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = hash(password);
		messages = new ArrayList<Message>();
		receivers = new ArrayList<>();
		aliases = new HashMap<User, String>();
	}

	public boolean login(String password) {
		String hashedInputPass = hash(password);
		if (this.password.equals(hashedInputPass)) {
			return true;
		}
		return false;
	}

	public String getUserID() {
		return userID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public boolean getMessage(User user, String contentMessage) {
		for (Message message : messages) {
			if (message.sender == user && contentMessage.equals(message.messageContent)) {
				return true;
			}
		}
		return false;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public List<Message> getMessageByKeywords(String keyword) {
		List<Message> listMessages = new ArrayList<>();
		for (Message message : messages) {
			if (message.getMessageContent().contains(keyword)) {
				listMessages.add(message);
			}
		}
		return listMessages;
	}

	public ArrayList<User> getReceiver() {
		return receivers;
	}

	public void setReceiver(ArrayList<User> receiver) {
		this.receivers = receiver;
	}

	public void addConversation(User user) {
		boolean flag = true;
		for (User receiver : receivers) {
			if (user.getId() == receiver.getId()) {
				flag = false;
				break;
			}
		}
		if (flag) {
			receivers.add(user);
		}
	}

	public List<String> getConversations(User user) {
		List<String> nameOfReceivers = new ArrayList<>();
		for (User receiver : receivers) {
			nameOfReceivers.add(receiver.userName);
		}
		return null;
	}

	public boolean setAlias(User assignee, String aliasName) {
		aliases.put(assignee, aliasName);
		return true;
	}

	public String getAlias(User assignee) {
		String alias = aliases.get(assignee);
		return alias;
	}
}