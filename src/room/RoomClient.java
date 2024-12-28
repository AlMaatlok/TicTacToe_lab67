package room;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RoomClient {
    private RoomServiceInterface roomService;
    private String serverAddress;
    private String playerName;
    private String roomId;

    public RoomClient(RoomServiceInterface roomService, String playerName, String roomId) {
        this.roomService = roomService;
        this.playerName = playerName;
        this.roomId = roomId;
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

            System.out.println("Gracz " + playerName + " opuścił pokój.");

        } catch (RemoteException e) {
            System.out.println("Błąd podczas opuszczania pokoju: " + e.getMessage());
        }
    }

}
