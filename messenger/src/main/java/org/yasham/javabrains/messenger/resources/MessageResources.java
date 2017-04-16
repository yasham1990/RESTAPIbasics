package org.yasham.javabrains.messenger.resources;

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

import org.yasham.javabrains.messenger.message.Message;
import org.yasham.javabrains.messenger.resources.beans.MessageFilterBean;
import org.yasham.javabrains.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResources {

	MessageService messageService = new MessageService();

	/*@GET
	public List<Message> getMessages(@QueryParam("year") int year,@QueryParam("start") int start,@QueryParam("size") int size) {
		if (year > 0)
			return messageService.getAllMessagesForYear(year);
		if (start >= 0 && size>=0)
			return messageService.getAllMessagesPaginated(start, size);
		return messageService.getAllMessages();
	}*/
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterBean.getYear());
		if (filterBean.getStart() > 0 && filterBean.getSize()>0)
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId,@Context UriInfo uriInfo) {
		Message message=messageService.getMessage(messageId);
		message.addLinks(getUriForSelf(uriInfo,message), "self");
		message.addLinks(getUriForProfile(uriInfo,message), "profile");
		message.addLinks(getUriForComments(uriInfo,message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getBaseUriBuilder().path(MessageResources.class).path(MessageResources.class,"getCommentResource")
				.path(CommentResource.class).resolveTemplate("messageId", message.getId())
				.build().toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build().toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getBaseUriBuilder().path(MessageResources.class).path(Long.toString(message.getId())).build().toString();
		return uri;
	}
	/*@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}*/
	
	@POST
	public Response addMessage(Message message,@Context UriInfo uriInfo) {
		Message newMessage=messageService.addMessage(message);
		String newId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build(); 
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
