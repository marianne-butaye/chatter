package ca.ulaval.glo4002.chatter.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SequentialNameRoomFactoryTest {

    private SequentialNameRoomFactory factory;

    @Before
    public void createFactory() {
        factory = new SequentialNameRoomFactory();
        factory.clearCache();
    }

    @Test
    public void createsRoomWithUniqueNameSameAsNameIfItsTheFirstWithThisName() {
        String roomName = "name";

        Room room = factory.createRoom(roomName);

        assertEquals(roomName, room.getUniqueName());
    }

    @Test
    public void createsRoomWithUniqueNameHavingASuffixIfItsTheSecond() {
        String roomName = "name";
        factory.createRoom(roomName);

        Room room = factory.createRoom(roomName);

        assertEquals(roomName + "_1", room.getUniqueName());
    }

    @Test
    public void createsRoomIncrementsSuffixForEachRoomCreated() {
        String roomName = "name";
        factory.createRoom(roomName);
        factory.createRoom(roomName);
        factory.createRoom(roomName);

        Room room = factory.createRoom(roomName);

        assertEquals(roomName + "_3", room.getUniqueName());
    }

}
