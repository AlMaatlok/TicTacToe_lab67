package connection;

import core.GameEngine;
import room.Room;

import java.util.*;

public class SessionManager {
    private final Map<String, Room> sessions = new HashMap<>();

    public Room createSession(String roomName, String password) {
        String token = UUID.randomUUID() + "@" + roomName;
        if (sessions.containsKey(token)) {
            throw new IllegalArgumentException("room.Room with this token already exists.");
        }
        Room room = new Room(roomName, password, null, null, new GameEngine());
        sessions.put(token, room);
        return room;
    }

    public Room getSession(String roomToken) {
        if (!sessions.containsKey(roomToken)) {
            throw new IllegalArgumentException("room.Room not found.");
        }
        return sessions.get(roomToken);
    }

    public void removeSession(String roomToken) {
        if (!sessions.containsKey(roomToken)) {
            throw new IllegalArgumentException("room.Room not found.");
        }
        sessions.remove(roomToken);
    }

    public List<Room> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }
}
