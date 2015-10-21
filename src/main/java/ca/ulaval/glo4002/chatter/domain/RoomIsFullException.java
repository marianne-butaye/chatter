package ca.ulaval.glo4002.chatter.domain;

public class RoomIsFullException extends RuntimeException {

    public RoomIsFullException() {
        super("This room is full");
    }

    private static final long serialVersionUID = 1L;

}
