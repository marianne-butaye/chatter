package ca.ulaval.glo4002.chatter.domain;

import java.util.HashMap;
import java.util.Map;

public class SequentialNameRoomFactory implements RoomFactory {

    private static Map<String, Integer> nameCache = new HashMap<>();

    public Room createRoom(String roomName) {
        return new Room(roomName, createUniqueName(roomName));
    }

    private String createUniqueName(String name) {
        synchronized (nameCache) {
            if (!nameCache.containsKey(name)) {
                nameCache.put(name, 1);
                return name;
            } else {
                Integer currentCount = nameCache.get(name);
                nameCache.put(name, currentCount + 1);
                return name + "_" + currentCount;
            }
        }
    }

    void clearCache() {
        nameCache.clear();
    }

}
