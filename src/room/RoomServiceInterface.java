package room;

import core.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomServiceInterface extends Remote{
    String createRoom(String password, String roomName) throws RemoteException;

    boolean joinRoom(String playerToken, String roomToken, String password) throws RemoteException;

    void leaveRoom(String playerToken, String roomToken) throws RemoteException;

    int resetRoom(String playerToken, String roomToken) throws RemoteException;

    int checkPlayersInRoom(String token) throws RemoteException;

    boolean hasRoomWithToken(String roomToken) throws RemoteException;

    String getOponent(String roomToken, String playerToken) throws RemoteException;

    boolean isYourTurn(Player player) throws RemoteException;

    String getPlayerWhosTurn(String roomToken) throws RemoteException;

    String getBoardInfo(String roomToken) throws RemoteException;

    boolean deleteRoom(String roomToken, String password) throws RemoteException;

    boolean makeMove(String token, String roomToken, int row, int col) throws RemoteException;

    String checkWinner(String roomToken) throws RemoteException;

    void listRooms() throws RemoteException;

    void notifyPlayersAboutLeave(String playerName) throws RemoteException;

}