package org.lcolodete.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.lcolodete.javabrains.messenger.database.DatabaseClass;
import org.lcolodete.javabrains.messenger.model.Comment;
import org.lcolodete.javabrains.messenger.model.ErrorMessage;
import org.lcolodete.javabrains.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = this.messages.get(messageId).getComments();
		return new ArrayList<>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {

		Message message = this.messages.get(messageId);
		if (message == null) {
//			throw new WebApplicationException(Status.NOT_FOUND);

			ErrorMessage errorMessage = new ErrorMessage("Message id "+messageId+ " not found.", 404, "http://javabrains.koushik.org");
			Response response = Response.status(Status.NOT_FOUND)
										.entity(errorMessage)
										.build();

			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = message.getComments();
		Comment comment = comments.get(commentId);
		if (comment == null) {
//			throw new WebApplicationException(Status.NOT_FOUND);
//			throw new WebApplicationException(response);

			ErrorMessage errorMessage = new ErrorMessage("Comment with id "+commentId+ " not found.", 404, "http://javabrains.koushik.org");
			Response response = Response.status(Status.NOT_FOUND)
					.entity(errorMessage)
					.build();
			throw new NotFoundException(response);
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = this.messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = this.messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = this.messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
