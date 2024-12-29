package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {

        int port = 1099;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p") && i + 1 < args.length) {
                try {
                    port = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port number. Using default port 1099.");
                }
            }
        }

        try {
            RoomServiceInterface roomService = new RoomServiceImpl();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("roomService", roomService);
            System.out.println("Server is ready on port " + port);

        } catch (RemoteException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
