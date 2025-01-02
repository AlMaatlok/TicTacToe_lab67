package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomServiceInterface extends Remote{
    String createRoom(String password, String roomName) throws RemoteException;

    int joinRoom(String playerToken, String roomToken, String password) throws RemoteException;


    int resetRoom(String playerToken, String roomToken) throws RemoteException;

    int checkPlayersInRoom(String token) throws RemoteException;

    boolean hasRoomWithToken(String roomToken) throws RemoteException;

    String getOponent(String roomToken, String playerToken) throws RemoteException;

    boolean isYourTurn(String roomToken, String playerToken) throws RemoteException;

    String getPlayerWhosTurn(String roomToken) throws RemoteException;

    String getBoardInfo(String roomToken) throws RemoteException;

    int deleteRoom(String roomToken, String password) throws RemoteException;

    boolean makeMove(String token, String roomToken, int row, int col) throws RemoteException;

    String checkWinner(String roomToken) throws RemoteException;

    String listRooms() throws RemoteException;

    String getStats(String playerToken, String roomToken) throws RemoteException;
}
