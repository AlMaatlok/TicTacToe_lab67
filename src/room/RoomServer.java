package room;

import connection.SessionManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RoomServer {
    private SessionManager sessionManager;

    public RoomServer(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void startSever() {
        try {
            RoomServiceImpl roomService = new RoomServiceImpl(sessionManager);
            RoomServiceImpl stub = (RoomServiceImpl) UnicastRemoteObject.exportObject(roomService, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("RoomService", stub);

            System.out.println("Server started successfully and bound to RoomService.");

        } catch (Exception e) {
            System.out.println("Failed to start the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
