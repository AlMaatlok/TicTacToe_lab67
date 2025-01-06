package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomServiceImpl extends UnicastRemoteObject implements RoomServiceInterface {
    private static Map<String, Room> rooms = new HashMap<>();

    public RoomServiceImpl()  throws RemoteException {
        super();
    }

    @Override
    public String createRoom(String password, String roomName)  {
        if(rooms.values().stream().anyMatch(room -> room.getRoomName().equals(roomName)))
            return "Can't create new room with room name " + roomName +". Room with that name already exists! Try again";

        String token = UUID.randomUUID() + "@" + roomName;

        if(rooms.containsKey(token))
            return "Can't create new room with token " + token +". Room with that token already exists! Try again";

        rooms.put(token, new Room(roomName, password, token));
        return "Created room named " + roomName + ". You can connect to it via token " + token;
    }
    @Override
    public int joinRoom(String playerToken, String roomToken, String password)  {
        if (!rooms.containsKey(roomToken)) {
            return 0;
        }

        Room room = rooms.get(roomToken);
        return room.joinRoom(playerToken, password);
    }
    @Override
    public int resetRoom(String playerToken, String roomToken)  {
        if (!rooms.containsKey(roomToken)) {
            return -1;
        }

        Room room = rooms.get(roomToken);
        if (room.getPlayers().containsKey("X") && room.getPlayers().get("X").getPlayerToken().equals(playerToken) ||
                room.getPlayers().containsKey("O") && room.getPlayers().get("O").getPlayerToken().equals(playerToken)) {
            room.resetRoom();
            return 1;
        } else {
            System.out.println("Player not found in the room.");
            return -2;
        }
    }

    @Override
    public int checkPlayersInRoom(String roomToken) {
        if(!rooms.containsKey(roomToken)) return -1;
        Room room = rooms.get(roomToken);
        return room.getPlayersNumber();
    }


    @Override
    public boolean hasRoomWithToken(String roomToken)  {
        return rooms.containsKey(roomToken);
    }

    @Override
    public String getOponent(String roomToken, String playerToken) {
        Room room = rooms.get(roomToken);
        String opponentToken = playerToken.equals(room.getPlayers().get("X").getPlayerToken()) ? "O" : "X";

        return room.getPlayers().get(opponentToken).getPlayerToken();
    }


    @Override
    public boolean isYourTurn(String roomToken, String playerToken) {
        Room room = rooms.get(roomToken);
        return room.isYourTurn(playerToken);
    }

    @Override
    public String getPlayerWhosTurn(String roomToken)  {
        return rooms.get(roomToken).getPlayerWhosTurn();
    }

    @Override
    public String getBoardInfo(String roomToken)  {
        Room room = rooms.get(roomToken);
        if (room == null) {
            throw new IllegalArgumentException("Room with this token does not exist");
        }
        return room.getBoardInfo();
    }

    @Override
    public int deleteRoom(String roomToken, String password)  {
        if(!rooms.containsKey(roomToken)){
            return -1;
        }
        Room room = rooms.get(roomToken);
        if(!room.getPassword().equals(password)){
            return -2;
        }
        else{
            rooms.remove(roomToken);
            return 1;
        }
    }

    @Override
    public boolean makeMove(String roomToken, String playerToken, int row, int col) {
        if (!rooms.containsKey(roomToken)) {
            return false;
        }

        Room room = rooms.get(roomToken);
        return room.makeMove(row, col);
    }

    @Override
    public String checkWinner(String roomToken)  {
        if(!rooms.containsKey(roomToken)){
            return "Room with this token does not exist";
        }
        return rooms.get(roomToken).checkWinner();
    }

    @Override
    public String listRooms()  {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Room> entry : rooms.entrySet()){
            Room room = entry.getValue();
            sb.append("Room: ").append(room.getRoomName()).append(", token: ").append(room.getRoomToken()).append(", number of players: ").append(room.getPlayersNumber()).append("/2").append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getStats(String playerToken, String roomToken) {
        if(!rooms.containsKey(roomToken)){
            return "Room with this token does not exist";
        }
        else return rooms.get(roomToken).getStatistics(playerToken);
    }
}

