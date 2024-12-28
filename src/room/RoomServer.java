package room;

import connection.SessionManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RoomServer {
    private static boolean isExported = false;
    private static RoomServiceInterface roomService;

    public RoomServer(SessionManager sessionManager) throws RemoteException {
        roomService = new RoomServiceImpl(sessionManager);
    }

    public void startServer() throws RemoteException {
        if (isExported) {
            System.out.println("Obiekt już został wyeksportowany.");
            return;
        }

        try {
            Registry registry = LocateRegistry.createRegistry(10000);
            if (roomService != null) {
                UnicastRemoteObject.exportObject(roomService, 0);  // Tylko raz
                registry.rebind("RoomService", roomService);
                isExported = true;
                System.out.println("Serwis pokoju uruchomiony.");
            }
        } catch (RemoteException e) {
            System.err.println("Nie udało się uruchomić serwera: " + e.getMessage());
            throw e;
        }
    }
}
