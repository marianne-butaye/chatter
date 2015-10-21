package ca.ulaval.glo4002.chatter.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.chatter.domain.RoomIsFullException;
import ca.ulaval.glo4002.chatter.domain.SequentialNameRoomFactory;
import ca.ulaval.glo4002.chatter.persistence.HibernateRoomRepository;
import ca.ulaval.glo4002.chatter.services.CannotDeleteRoomException;
import ca.ulaval.glo4002.chatter.services.RoomCreationDto;
import ca.ulaval.glo4002.chatter.services.RoomService;

@Path("/rooms")
public class ChatterResource {

    private RoomService roomService;

    public ChatterResource() {
        // Ideally dependencies should be resolved in a more stylish way.
        roomService = new RoomService(new SequentialNameRoomFactory(), new HibernateRoomRepository());
    }

    public ChatterResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoom(RoomCreationDto form) {
        String generatedRoomName = roomService.createRoom(form);
        return Response.created(URI.create("/rooms/" + generatedRoomName)).build();
    }

    @POST
    @Path("/{uniqueName}/users")
    public Response joinRoom(@PathParam(value = "uniqueName") String uniqueName) {
        try {
            roomService.join(uniqueName);
            return Response.ok().build();
        } catch (RoomIsFullException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uniqueName}/users")
    public Response leaveRoom(@PathParam(value = "uniqueName") String uniqueName) {
        roomService.leave(uniqueName);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{uniqueName}")
    public Response deleteRoom(@PathParam(value = "uniqueName") String uniqueName) {
        try {
            roomService.delete(uniqueName);
            return Response.ok().build();
        } catch (CannotDeleteRoomException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
