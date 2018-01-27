package org.lcolodete.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.lcolodete.javabrains.messenger.model.Message;
import org.lcolodete.javabrains.messenger.resources.beans.MessageFilterBean;
import org.lcolodete.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService messageService = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("getJsonMessages");
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("getXmlMessages");
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = this.messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		
		return Response.created(uri)
						.entity(newMessage)
						.build();
		
//		return Response.status(Status.CREATED)
//						.entity(newMessage)
//						.build();
		//return this.messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return this.messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		this.messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = this.messageService.getMessage(id);
		
		//FORMA 1
		//String uri = uriInfo.getAbsolutePath().toASCIIString();
		
		//FORMA 2
		String uri = getUriForSelf(uriInfo, message);
		
		message.addLink(uri, "self");
		
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		
		message.addLink(getUriForComments(uriInfo, message), "comments");
		
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri =
		uriInfo.getBaseUriBuilder()
			   .path(MessageResource.class)
			   .path(MessageResource.class, "getCommentResource")
			   .path(CommentResource.class) // Do not need to do this, but it doesn't hurt!
			   .resolveTemplate("messageId", message.getId())
			   .build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri =
		uriInfo.getBaseUriBuilder()
			   .path(ProfileResource.class)
			   .path(message.getAuthor())
			   .build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder()              // http://localhost:8080/messenger/webapi
			   .path(MessageResource.class)      		//                                       /messages
			   .path(Long.toString(message.getId()))    //                                                /1
			   .build()
			   .toString();
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
