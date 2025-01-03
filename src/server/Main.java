package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    static RoomServiceImpl roomService;
    public static void main(String[] args) {

        try {
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

            roomService = new RoomServiceImpl();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("roomService", roomService);
            System.out.println("Server is ready on port " + port);

            ServerSocket serverSocket = new ServerSocket(port + 1);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                Thread clientTread = new ObserverThread(clientSocket);
                clientTread.start();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
