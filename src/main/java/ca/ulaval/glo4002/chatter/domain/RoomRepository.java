package ca.ulaval.glo4002.chatter.domain;

public interface RoomRepository {

    void persist(Room room);

    Room findByUniqueName(String uniqueName);

    void delete(Room room);

}
