package client;

import server.RoomServiceInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    static String playerToken;
    static String playerName;

    static RoomServiceInterface roomService;

    static String connectedRoomToken = "";

    static String host;

    static int port;

    public static void main(String[] args) {
        host = "127.0.0.1";
        port = 1099;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-h") && i + 1 < args.length) {
                host = args[i + 1];
            } else if (args[i].equals("-p") && i + 1 < args.length) {
                try {
                    port = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port number. Using default port 1099.");
                }
            }
        }

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            roomService = (RoomServiceInterface) registry.lookup("roomService");

            Scanner scanner = new Scanner(System.in);
            UI UIHandler = new UI();

            System.out.println("Welcome to TicTacToe!");
            System.out.print("Enter your player name: ");
            playerName = scanner.nextLine();
            playerToken = UUID.randomUUID() + "@" + playerName;
            System.out.println("Welcome, " + playerToken.split("@")[1] + "!");
            System.out.println();

            UIHandler.start();

        } catch (Exception e) {
            System.out.println("Client failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
