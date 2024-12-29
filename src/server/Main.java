package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args)  {

        try {
            RoomServiceInterface roomService = new RoomServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("roomService", roomService);
            System.out.println("Server is ready");

        }
        catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
