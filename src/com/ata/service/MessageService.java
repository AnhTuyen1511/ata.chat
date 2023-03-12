package com.ata.service;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ata.chat.File;
import com.ata.chat.Group;
import com.ata.chat.Message;
import com.ata.chat.User;
import com.ata.data.Database;

public class MessageService {
	Database data;

	public void sendMessagetoGroup(User sender, Group group, String messageContent) {
		Message message = new Message(sender, group, messageContent);
		group.getMessages().add(message);
	}

	public void sendMessagetoReceiver(User sender, User receiver, String messageContent) {
		Message message = new Message(sender, receiver, messageContent);
		receiver.getMessages().add(message);
	}

//	public void sendFile(User sender, Object recipient, File file) {
//	    // save file to a folder with unique ID
//	    String fileId = UUID.randomUUID().toString();
//	    File newFile = new File("" + fileId);
//	    // copy file to new location
//	    Files.copy(file.toPath(), newFile.toPath());
//	    Message message = new FileMessage(UUID.randomUUID(), sender, recipient, new Date());
//	    saveMessage(message);
//	    // send message to recipient
//	}

	public List<Message> getTopLatestMessage(User sender, User receiver, int numberOfLatestMessages, int exception) {
		List<Message> messagesOfSender = sender.getMessages();
		List<Message> messagesOfReceiver = receiver.getMessages();
		List<Message> topLatestMessage = new ArrayList<>();
		int startFrom = messagesOfSender.size() - 1 - exception;
		int endAt = startFrom - numberOfLatestMessages;

		for (int i = startFrom; i > numberOfLatestMessages; i--) {
			if (startFrom >= 0 && endAt >= 0) {
				topLatestMessage.add(messagesOfSender.get(i));
				topLatestMessage.add(messagesOfReceiver.get(i));
			} else {
				break;
			}
		}
		return topLatestMessage;
	}

	public void deleteMessage(Message message) {
		Object receiver = message.getReceiver();

		if (receiver instanceof User) {
			User user = (User) receiver;
			user.getMessages().remove(message);
		} else {
			Group group = (Group) receiver;
			group.getMessages().remove(message);
		}

	}

	public List<Message> findMessageByKeywords(User user, String keyword) {
		List<Message> result = user.getMessageByKeywords(message -> message.messageContent.contains(keyword));
		return result;
	}

	public List<File> getFilesInGroup(Group group) {
		List<File> files = group.getFiles();
		return files;
	}
}