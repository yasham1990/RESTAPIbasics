package org.yasham.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.yasham.javabrains.messenger.database.DatabaseClass;
import org.yasham.javabrains.messenger.message.Comment;
import org.yasham.javabrains.messenger.message.ErrorMessage;
import org.yasham.javabrains.messenger.message.Message;

public class CommentService {

	private static Map<Long, Message> messages = DatabaseClass.getMessages();

	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}

	/*
	 * public List<Message> getAllMessagesForYear(int year) { List<Message>
	 * messageForYear=new ArrayList<>(); Calendar cal=Calendar.getInstance();
	 * for(Message message:messages.values()) {
	 * cal.setTime(message.getCreated()); if(cal.get(Calendar.YEAR)==year){
	 * messageForYear.add(message); } } return messageForYear; }
	 */
	//Lot Of other Exception -ClientErrorException,ServorErrorException,RedirectionErrorException

	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage=new ErrorMessage("Not Found", 404, "http://javabrains.yasham.org");
		Response response= Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Message message = messages.get(messageId);
		if (message == null)
			throw new WebApplicationException(response);
		Map<Long, Comment> comments = message.getComments();
		Comment comment = comments.get(commentId);
		if (comment == null)
			throw new WebApplicationException(response);
		return comment;
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0)
			return null;
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}

}
