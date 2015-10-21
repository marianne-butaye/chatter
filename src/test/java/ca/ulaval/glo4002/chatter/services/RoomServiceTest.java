package ca.ulaval.glo4002.chatter.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.chatter.domain.Room;
import ca.ulaval.glo4002.chatter.domain.RoomFactory;
import ca.ulaval.glo4002.chatter.domain.RoomRepository;

public class RoomServiceTest {

    private Room createdRoom;
    private Room existingRoom;

    private RoomService service;
    private RoomFactory roomFactory;
    private RoomRepository roomRepository;

    @Before
    public void createService() {
        roomFactory = mock(RoomFactory.class);
        roomRepository = mock(RoomRepository.class);
        addCreatedRoom();
        addExistingRoom();
        service = new RoomService(roomFactory, roomRepository);
    }

    @Test
    public void createRoomCreatesANewRoom() {
        RoomCreationDto form = new RoomCreationDto();
        form.name = "roomName";

        service.createRoom(form);

        verify(roomFactory).createRoom(form.name);
    }

    @Test
    public void createRoomSavesCreatedRoom() {
        Room room = mock(Room.class);
        willReturn(room).given(roomFactory).createRoom(anyString());

        service.createRoom(new RoomCreationDto());

        verify(roomRepository).persist(room);
    }

    @Test
    public void createRoomReturnsRoomUniqueName() {
        String uniqueName = "uniqueName";
        willReturn(uniqueName).given(createdRoom).getUniqueName();

        String roomName = service.createRoom(new RoomCreationDto());

        assertEquals(uniqueName, roomName);
    }

    @Test
    public void joinFindsRoomByUniqueName() {
        String uniqueName = "uniqueName";

        service.join(uniqueName);

        verify(roomRepository).findByUniqueName(uniqueName);
    }

    @Test
    public void joinAddsUserToRoom() {
        service.join("uniqueName");

        verify(existingRoom).addUser();
    }

    @Test
    public void joinPersistsRoom() {
        service.join("uniqueName");

        verify(roomRepository).persist(existingRoom);
    }

    @Test
    public void leaveFindsRoomByUniqueName() {
        String uniqueName = "uniqueName";

        service.leave(uniqueName);

        verify(roomRepository).findByUniqueName(uniqueName);
    }

    @Test
    public void leaveMakesUseLeaveROom() {
        service.leave("uniqueName");

        verify(existingRoom).leave();
    }

    @Test
    public void leavePersistsRoom() {
        service.leave("uniqueName");

        verify(roomRepository).persist(existingRoom);
    }

    @Test
    public void deleteFindsRoomByUniqueName() {
        String uniqueName = "uniqueName";
        willReturn(true).given(existingRoom).canBeDeleted();

        service.delete(uniqueName);

        verify(roomRepository).findByUniqueName(uniqueName);
    }

    @Test
    public void deleteDeletesTheRoomFoundIfItCanBeDeleted() {
        willReturn(true).given(existingRoom).canBeDeleted();

        service.delete(null);

        verify(roomRepository).delete(existingRoom);
    }

    @Test
    public void deleteDoesNotDeleteTheRoomFoundIfItCannotBeDeleted() {
        willReturn(false).given(existingRoom).canBeDeleted();

        try {
            service.delete(null);
        } catch (CannotDeleteRoomException e) {
        }

        verify(roomRepository, never()).delete(existingRoom);
    }

    @Test(expected = CannotDeleteRoomException.class)
    public void deleteThrowsExceptionIfRoomCannotBeDeleted() {
        willReturn(false).given(existingRoom).canBeDeleted();

        service.delete(null);
    }

    private void addCreatedRoom() {
        createdRoom = mock(Room.class);
        willReturn(createdRoom).given(roomFactory).createRoom(anyString());
    }

    private void addExistingRoom() {
        existingRoom = mock(Room.class);
        willReturn(existingRoom).given(roomRepository).findByUniqueName(anyString());
    }

}
