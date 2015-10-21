package ca.ulaval.glo4002.chatter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"uniqueName"}) )
public class Room {

    private final static int MAX_USER_PER_ROOM = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "uniqueName")
    private String uniqueName;

    @Column(name = "userCount")
    private int userCount;

    Room() {
    }

    public Room(String name, String uniqueName) {
        this.name = name;
        this.uniqueName = uniqueName;
        this.userCount = 0;
    }

    public String getUniqueName() {
        return uniqueName;

    }

    public void addUser() {
        if (userCount >= MAX_USER_PER_ROOM) {
            throw new RoomIsFullException();
        }
        userCount++;
    }

    public boolean canBeDeleted() {
        return userCount == 0;
    }

    public void leave() {
        userCount--;
    }

}
