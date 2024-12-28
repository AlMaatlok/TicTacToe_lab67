package room;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RoomClient {
    private RoomServiceInterface roomService;
    private String playerName;
    private String roomId;

    public RoomClient(RoomServiceInterface roomService, String playerName, String roomId) {
        this.roomService = roomService;
        this.playerName = playerName;
        this.roomId = roomId;
    }

    public void makeMove(int x, int y) throws RemoteException {
        try {
            boolean success = roomService.makeMove(playerName, roomId, x, y);
            if (success) {
                System.out.println("Move made at position (" + x + ", " + y + ")");
            } else {
                System.out.println("Move failed. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error making move: " + e.getMessage());
        }
    }
    public void connectToServer(String serverAddress) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverAddress);
            roomService = (RoomServiceInterface) registry.lookup("RoomService");
            System.out.println("Connected to server: " + serverAddress);
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void createRoom(String roomName, String password) {
        try {
            String roomToken = roomService.createRoom(roomName, password);
            System.out.println("Room created with token: " + roomToken);
        } catch (RemoteException e) {
            System.out.println("Error creating room: " + e.getMessage());
        }
    }

    public void joinRoom(String playerName, String roomToken, String password) {
        try {
            boolean success = roomService.joinRoom(playerName, roomToken, password);
            if (success) {
                System.out.println(playerName + " joined room: " + roomToken);
            } else {
                System.out.println("Failed to join room: " + roomToken);
            }
        } catch (RemoteException e) {
            System.out.println("Error joining room: " + e.getMessage());
        }
    }

    public void displayGameState() {
        try {
            String gameState = roomService.getBoardInfo("roomToken");
            System.out.println("Current game state:\n" + gameState);
        } catch (RemoteException e) {
            System.out.println("Error displaying game state: " + e.getMessage());
        }
    }


    public void disconnect() {
        try {
            roomService.leaveRoom(roomId, playerName);

            UnicastRemoteObject.unexportObject(roomService, true);

            System.out.println("Player " + playerName + " left the room.");

        } catch (RemoteException e) {
            System.out.println("Error leaving the room " + e.getMessage());
        }
    }

}
