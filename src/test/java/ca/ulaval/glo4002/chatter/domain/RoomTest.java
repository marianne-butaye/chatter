package ca.ulaval.glo4002.chatter.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {

    private Room room;

    @Before
    public void createRoom() {
        room = new Room(null, null);
    }

    @Test
    public void cannotAddMoreThan3Users() {
        room.addUser();
        room.addUser();
        room.addUser();

        try {
            room.addUser();
            fail("Should not be able to add a 4th user");
        } catch (Exception e) {
        }
    }

    @Test
    public void canDeleteEmptyRoom() {
        boolean canBeDeleted = room.canBeDeleted();

        assertTrue(canBeDeleted);
    }

    @Test
    public void cannotDeleteRoomIfItHasUsers() {
        room.addUser();

        boolean canBeDeleted = room.canBeDeleted();

        assertFalse(canBeDeleted);
    }

    @Test
    public void canLeaveRoom() {
        room.addUser();

        room.leave();

        assertTrue("Room should become empty thus it can be deleted", room.canBeDeleted());
    }

}
