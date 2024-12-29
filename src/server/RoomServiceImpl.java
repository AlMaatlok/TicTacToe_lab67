package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RoomServiceImpl extends UnicastRemoteObject implements RoomServiceInterface {
    private static Map<String, Room> rooms = new HashMap<>();

    public RoomServiceImpl()  throws RemoteException {
        super();
    }

    @Override
    public String createRoom(String password, String roomName)  {
        String token = UUID.randomUUID() + "@" + roomName;

        if(rooms.containsKey(token))
            return "Can't create new room with token " + token +". Room with that token already exists! Try again";

        rooms.put(token, new Room(token, password, roomName));
        return "Created room named " + roomName + ". You can connect to it via token " + token;
    }
    @Override
    public int joinRoom(String playerToken, String roomToken, String password)  {
        if (!rooms.containsKey(roomToken)) {
            //System.out.println("Room doesn't exist.");
            return 0;
        }

        Room room = rooms.get(roomToken);
        return room.joinRoom(playerToken, password);
    }

    @Override
    public int leaveRoom(String roomToken, String playerName) throws RemoteException {
        if (!rooms.containsKey(roomToken)) {
            return 0;
        }
        Room room = rooms.get(roomToken);
        boolean playerRemoved = room.getPlayers().values()
                .removeIf(player -> player.getPlayerName().equals(playerName));
        return playerRemoved ? 1 : -1; // 1 oznacza sukces, -1 brak gracza w pokoju

    }

    @Override
    public int resetRoom(String playerToken, String roomToken)  {
        if (!rooms.containsKey(roomToken)) {
            return -1;
        }

        Room room = rooms.get(roomToken);
        if (room.getPlayers().containsKey("X") && room.getPlayers().get("X").getPlayerName().equals(playerToken) ||
                room.getPlayers().containsKey("O") && room.getPlayers().get("O").getPlayerName().equals(playerToken)) {
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
        String opponentToken = playerToken.equals("X") ? "O" : "X";
        return room.getPlayers().get(opponentToken).getPlayerName();
    }


    @Override
    public boolean isYourTurn(String playerToken) {
        return rooms.containsKey(playerToken);
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
        Player player = room.getPlayers().get(playerToken);
        return room.makeMove(player, row, col);
    }

    @Override
    public String checkWinner(String roomToken)  {
        if(!rooms.containsKey(roomToken)){
            return "Room with this token does not exist";
        }
        return rooms.get(roomToken).checkWinner();
    }

    @Override
    public void listRooms()  {
        for(Map.Entry<String, Room> entry : rooms.entrySet()){
            Room room = entry.getValue();
            System.out.println("Room: " + room.getRoomName() + ", token: " + room.getRoomToken() + ", number of players: " + room.getPlayersNumber() + "/2\\n");
        }
    }

}
