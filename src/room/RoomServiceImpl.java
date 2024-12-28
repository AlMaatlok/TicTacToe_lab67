package room;

import connection.SessionManager;
import core.GameEngine;
import core.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomServiceImpl extends UnicastRemoteObject implements RoomServiceInterface {
    private static Map<String, Room> rooms = new HashMap<>();
    private static List<Player> players;
    private GameEngine game;
    private final SessionManager sessionManager;

    public RoomServiceImpl(SessionManager sessionManager)  throws RemoteException {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public String createRoom(String password, String roomName)  {
        Room room = sessionManager.createSession(roomName, password);
        rooms.put(room.getRoomToken(), room);
        return "Created room with token " + roomName + "You can connect to it via token: " + room.getRoomToken();
    }

    @Override
    public boolean joinRoom(String playerToken, String roomToken, String password)  {
        if (!rooms.containsKey(roomToken)) {
            System.out.println("Room doesn't exist.");
            return false;
        }

        Room room = rooms.get(roomToken);
        if (room.getPassword().equals(password)) {
            if (room.getPlayers().size() < 2) {
                String token = (room.getPlayers().size() == 0) ? "X" : "O";
                room.getPlayers().put(token, new Player(playerToken));
                System.out.println("Joined as player " + token);
                return true;
            } else {
                System.out.println("Room is full.");
                return false;
            }
        } else {
            System.out.println("Invalid password.");
            return false;
        }
    }

    @Override
    public void leaveRoom(String roomId, String playerName) throws RemoteException {
        removePlayerFromRoom(playerName);

        notifyPlayersAboutLeave(playerName);
    }

    @Override
    public void notifyPlayersAboutLeave(String playerName) throws RemoteException {
        for (Player player : players) {
            if (!player.getPlayerName().equals(playerName)) {
                System.out.println("Gracz " + playerName + " opuścił pokój.");
            }
        }
    }

    public void removePlayerFromRoom(String playerName) {
        players.removeIf(player -> player.getPlayerName().equals(playerName));
        System.out.println("Gracz " + playerName + " został usunięty z pokoju.");
    }

    public void addPlayer(Player player) {
        players.add(player);
    }


    @Override
    public int resetRoom(String playerToken, String roomToken)  {
        if (!rooms.containsKey(roomToken)) {
            System.out.println("Room doesn't exist.");
            return -1;
        }

        Room room = rooms.get(roomToken);
        if (room.getPlayers().containsKey("X") && room.getPlayers().get("X").getPlayerName().equals(playerToken) ||
                room.getPlayers().containsKey("O") && room.getPlayers().get("O").getPlayerName().equals(playerToken)) {
            room.resetRoom();
            System.out.println("Room reset successful.");
            return 1;
        } else {
            System.out.println("Player not found in the room.");
            return -1;
        }
    }

    @Override
    public int checkPlayersInRoom(String token) {
        Room room = sessionManager.getSession(token);
        return room.getPlayersNumber();
    }


    @Override
    public boolean hasRoomWithToken(String roomToken)  {
        return rooms.containsKey(roomToken);
    }

    @Override
    public String getOponent(String roomToken, String playerToken) {
        Room room = sessionManager.getSession(roomToken);
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
            throw new IllegalArgumentException("Pokój o podanym tokenie nie istnieje.");
        }
        return room.getBoardInfo();
    }

    @Override
    public boolean deleteRoom(String roomToken, String password)  {
        if(!rooms.containsKey(roomToken)){
            System.out.println("room.Room doesn't exist.");
            return false;
        }
        else{
            rooms.remove(roomToken);
            return true;
        }
    }

    @Override
    public boolean makeMove(String roomToken, String playerToken, int row, int col) {
        Room room = sessionManager.getSession(roomToken);
        Player player = room.getPlayers().get(playerToken);
        return room.makeMove(player, row, col);
    }

    @Override
    public String checkWinner(String roomToken)  {
        return rooms.get(roomToken).checkWinner();
    }

    @Override
    public void listRooms()  {
        List<Room> rooms = sessionManager.getAllSessions();
           for(Room room : rooms){
               System.out.println("room.Room: " + room.getRoomName() + ", token: " + room.getRoomToken() + ", number of players: " + room.getPlayersNumber() + "/2\\n");
           }
    }
}
