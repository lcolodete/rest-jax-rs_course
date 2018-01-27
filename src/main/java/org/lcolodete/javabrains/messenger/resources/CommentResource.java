package org.lcolodete.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.lcolodete.javabrains.messenger.model.Comment;
import org.lcolodete.javabrains.messenger.service.CommentService;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return this.commentService.getAllComments(messageId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId,
							  Comment comment) {
		return this.commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, 
								 @PathParam("commentId") long commentId, Comment comment) {
		comment.setId(commentId);
		return this.commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public Comment removeComment(@PathParam("messageId") long messageId, 
								 @PathParam("commentId") long commentId ) {
		return this.commentService.removeComment(messageId, commentId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, 
							  @PathParam("commentId") long commentId ) {
		return this.commentService.getComment(messageId, commentId);
	}
	
//	@GET
//	public String test() {
//		return "new sub resource";
//	}
//	
//	@GET
//	@Path("/{commentId}")
//	public String test2(@PathParam("messageId") long messageId,
//						@PathParam("commentId") long commentId) {
//		return "method to return commentId : "+commentId + " for messageId : "+messageId;
//	}
	
}
