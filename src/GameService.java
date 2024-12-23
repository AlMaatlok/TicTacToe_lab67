import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface GameService extends Remote {

    String createGame(String playerName) throws RemoteException;

    String joinGame(String playerName) throws RemoteException;

    boolean makeMove(String gameId, int row, int col, char player) throws RemoteException;

    char[][] getGameState(String gameId) throws RemoteException;

    Map<String, Integer> getPlayerStats(String playerName) throws RemoteException;

    List<String> getActiveGames() throws RemoteException;
}
