package ca.ulaval.glo4002.chatter.services;

public class CannotDeleteRoomException extends RuntimeException {

    public CannotDeleteRoomException() {
        super("Cannot delete room");
    }

    private static final long serialVersionUID = 1L;

}
