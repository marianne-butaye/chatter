package ca.ulaval.glo4002.chatter.services;

import ca.ulaval.glo4002.chatter.domain.Room;
import ca.ulaval.glo4002.chatter.domain.RoomFactory;
import ca.ulaval.glo4002.chatter.domain.RoomRepository;

public class RoomService {

    private RoomFactory roomFactory;
    private RoomRepository roomRepository;

    public RoomService(RoomFactory roomFactory, RoomRepository roomRepository) {
        this.roomFactory = roomFactory;
        this.roomRepository = roomRepository;
    }

    public String createRoom(RoomCreationDto form) {
        Room room = roomFactory.createRoom(form.name);
        roomRepository.persist(room);
        return room.getUniqueName();

    }

    public void join(String uniqueName) {
        Room room = roomRepository.findByUniqueName(uniqueName);
        room.addUser();
        roomRepository.persist(room);

    }

    public void delete(String uniqueName) {
        Room room = roomRepository.findByUniqueName(uniqueName);
        if (room.canBeDeleted()) {
            roomRepository.delete(room);
        } else {
            throw new CannotDeleteRoomException();
        }
    }

    public void leave(String uniqueName) {
        Room room = roomRepository.findByUniqueName(uniqueName);
        room.leave();
        roomRepository.persist(room);
    }

}
