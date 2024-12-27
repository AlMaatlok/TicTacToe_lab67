import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class RoomServiceImpl implements RoomServiceInterface{
    private static Map<String, Room> rooms = new HashMap<>();
    private static List<Player> players;
    private GameEngine game;
    private final SessionManager sessionManager = new SessionManager();

    public RoomServiceImpl()  throws RemoteException {
        super();
    }

    @Override
    public String createRoom(String password, String roomName) throws RemoteException {
        Room room = sessionManager.createSession(roomName, password);
        return "Created room with token " + roomName + "You can connect to it via token: " + room.getRoomToken();
    }

    @Override
    public boolean joinRoom(String playerToken, String roomToken, String password) throws RemoteException {
        if(!rooms.containsKey(roomToken)){
            System.out.println("Room doesn't exist.");
            return false;
        }

        else{
            Room room = rooms.get(roomToken);

            if (room.getPassword().equals(password)) {

                if (room.getPlayerX() == null) {
                    room.setPlayerX(new Player(playerToken));
                    System.out.println("Joined as player X");
                    return true;
                }
                else if (room.getPlayerO() == null) {
                    room.setPlayerO(new Player(playerToken));
                    System.out.println("Joined as player O");
                    return true;
                }
                else {
                    System.out.println("Room is full.");
                    return false;
                }
            }
            else{
                System.out.println("Invalid password.");
                return false;
            }
        }
    }

    @Override
    public boolean leaveRoom(String playerToken, String roomToken) throws RemoteException {
        if(!rooms.containsKey(roomToken)){
            System.out.println("Room doesn't exist.");
            return false;
        }

        Room room = rooms.get(roomToken);
        if(room.getPlayerX() != null && room.getPlayerX().equals(playerToken)){
            room.setPlayerX(null);
            System.out.println("Player X left the room.");
            return true;
        }
        else if(room.getPlayerO() != null && room.getPlayerO().equals(playerToken)){
            room.setPlayerO(null);
            System.out.println("Player O left the room.");
            return true;
        }
        else{
            System.out.println("Player not found in the room.");
            return false;
        }
    }

    @Override
    public int resetRoom(String playerToken, String roomToken) throws RemoteException {
        if(!rooms.containsKey(roomToken)){
            System.out.println("Room doesn't exist.");
            return -1;
        }

        Room room = rooms.get(roomToken);
        if(room.getPlayerX() != null && room.getPlayerX().equals(playerToken) ||
                room.getPlayerO() != null && room.getPlayerO().equals(playerToken)){
            room.resetRoom();
            System.out.println("Room reset successful.");
            return 1;
        }
        else{
            System.out.println("Player not found in the room.");
            return -1;
        }
    }

    @Override
    public int checkPlayersInRoom(String token) throws RemoteException {
        return 0;
    }

    @Override
    public boolean hasRoomWithToken(String roomToken) throws RemoteException {
        return rooms.containsKey(roomToken);
    }

    @Override
    public String getOponent(String roomToken, String playerToken) throws RemoteException {
        return null;
    }

    @Override
    public boolean isYourTurn(String roomToken, String playerToken) throws RemoteException {
        return true;
        //return  rooms.get(roomToken).isYourTurn();
    }

    @Override
    public String getPlayerWhosTurn(String roomToken) throws RemoteException {
        return rooms.get(roomToken).getPlayerWhosTurn();
    }

    @Override
    public String getBoardInfo(String roomToken) throws RemoteException {
        return rooms.get(roomToken).getBoardInfo();
    }

    @Override
    public boolean deleteRoom(String roomToken, String password) throws RemoteException {
        if(!rooms.containsKey(roomToken)){
            System.out.println("Room doesn't exist.");
            return false;
        }
        else{
            rooms.remove(roomToken);
            return true;
        }
    }

    @Override
    public boolean makeMove(String token, String roomToken, int row, int col) throws RemoteException {
        //return rooms.get(roomToken).makeMove()
    }

    @Override
    public String checkWinner(String roomToken) throws RemoteException {
        return rooms.get(roomToken).checkWinner();
    }

    @Override
    public void listRooms() throws RemoteException {
        List<Room> rooms = sessionManager.getAllSessions();
           for(Room room : rooms){
               System.out.println("Room: " + room.getRoomName() + ", token: " + room.getRoomToken() + ", number of players: " + room.getPlayersNumber() + "/2\\n");
           }
    }
}
