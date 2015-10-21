package ca.ulaval.glo4002.chatter.rest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.chatter.domain.RoomIsFullException;
import ca.ulaval.glo4002.chatter.services.CannotDeleteRoomException;
import ca.ulaval.glo4002.chatter.services.RoomCreationDto;
import ca.ulaval.glo4002.chatter.services.RoomService;

public class ChatterResourceTest {

    private ChatterResource resource;
    private RoomService roomService;

    @Before
    public void createResource() {
        roomService = mock(RoomService.class);
        resource = new ChatterResource(roomService);
    }

    @Test
    public void createRoomForwardsRequestToService() {
        RoomCreationDto form = new RoomCreationDto();

        resource.createRoom(form);

        verify(roomService).createRoom(form);
    }

    @Test
    public void createRoomReturnsHttpCreatedWithGeneratedRoomName() {
        String roomName = "roomName";
        willReturn(roomName).given(roomService).createRoom(any(RoomCreationDto.class));

        Response response = resource.createRoom(null);

        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("/rooms/" + roomName, response.getHeaderString("Location"));
    }

    @Test
    public void joinRoomForwardsRequestToService() {
        String roomUniqueName = "name";

        resource.joinRoom(roomUniqueName);

        verify(roomService).join(roomUniqueName);
    }

    @Test
    public void joinRoomReturnsOk() {
        Response response = resource.joinRoom(null);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void joinRoomReturnsErrorIfRoomIsFull() {
        RoomIsFullException exception = new RoomIsFullException();
        willThrow(exception).given(roomService).join(anyString());

        Response response = resource.joinRoom(null);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(exception.getMessage(), response.getEntity().toString());
    }

    @Test
    public void leaveRoomForwardsRequestToService() {
        String roomUniqueName = "name";

        resource.leaveRoom(roomUniqueName);

        verify(roomService).leave(roomUniqueName);
    }

    @Test
    public void leaveRoomReturnsOk() {
        Response response = resource.leaveRoom(null);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteRoomForwardsRequestToService() {
        String roomUniqueName = "name";

        resource.deleteRoom(roomUniqueName);

        verify(roomService).delete(roomUniqueName);
    }

    @Test
    public void deleteRoomReturnsOk() {
        Response response = resource.deleteRoom(null);

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void deleteRoomReturnsErrorIfRoomCannotBeDeleted() {
        CannotDeleteRoomException exception = new CannotDeleteRoomException();
        willThrow(exception).given(roomService).delete(anyString());

        Response response = resource.deleteRoom(null);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(exception.getMessage(), response.getEntity().toString());
    }

}
