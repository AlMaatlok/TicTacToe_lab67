package app;

import connection.SessionManager;
import room.RoomClient;
import room.RoomServer;
import room.RoomServiceImpl;
import room.RoomServiceInterface;

public class TicTacToeApplication {
    public static void main(String[] args) {
        try {
            // Initialize SessionManager
            SessionManager sessionManager = new SessionManager();

            // Start the RoomServer
            RoomServer roomServer = new RoomServer(sessionManager);
            roomServer.startServer();

            // Create a RoomService object to interact with the server
            RoomServiceInterface roomService = new RoomServiceImpl(sessionManager);

            // Example: Client-side operations
            RoomClient roomClient = new RoomClient(roomService, "Player1", "roomToken");
            roomClient.connectToServer("localhost");  // Connect to the RMI server

            // Creating a new room
            roomClient.createRoom("TestRoom", "password123");

            // Joining the room
            roomClient.joinRoom("Player2", "roomToken", "password123");

            // Display the game state
            roomClient.displayGameState();

            // Example: Make a move
            roomClient.makeMove(0, 0);  // Player1 makes a move

            // Disconnect from the room
            roomClient.disconnect();

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}