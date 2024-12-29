package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class UI {
    public void start() throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        mainMenuUI(scan);
    }

    public void mainMenuUI(Scanner scan) {
        clearConsole();

        System.out.println("==============================================");
        System.out.println("Player: " + Main.playerName);
        System.out.println("==============================================");
        System.out.println("Main Menu:");
        System.out.println("1. Create a room");
        System.out.println("2. List all rooms");
        System.out.println("3. Join room");
        System.out.println("4. Delete room");
        System.out.println("5. Type 'exit' to end the program");

        System.out.println("Choose an option:");
        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        createRoomUI(scan);
                        break;

                    case "2":
                        listAllRoomsUI(scan);
                        break;

                    case "3":
                        joinRoomUI(scan);
                        break;

                    case "4":
                        deleteRoomUI(scan);
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {

        }
    }
    public void createRoomUI(Scanner scan) throws RemoteException {
        clearConsole();

        System.out.println("Player: " + Main.playerName);
        System.out.println("Provide name of the room: ");
        String roomName = scan.nextLine();
        System.out.println("Provide password of the room: ");
        String password = scan.nextLine();

        System.out.println(Main.roomService.createRoom(password, roomName));

        System.out.println("====================================");
        System.out.println("1. Main Menu");
        System.out.println("2. List all rooms");
        System.out.println("3. Join room");
        System.out.println("4. Delete room");
        System.out.println("5. Type 'exit' to end the program");
        System.out.println("Choose an option:");

        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        mainMenuUI(scan);
                        break;

                    case "2":
                        listAllRoomsUI(scan);
                        break;

                    case "3":
                        joinRoomUI(scan);
                        break;

                    case "4":
                        deleteRoomUI(scan);
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {
        }
    }

    public void listAllRoomsUI(Scanner scan) throws RemoteException {
        clearConsole();

        System.out.println("Player: " + Main.playerName);
        System.out.println("=====================================");
        System.out.println("Rooms:");
        System.out.println(Main.roomService.listRooms());

        System.out.println("=====================================");
        System.out.println("1. Main Menu");
        System.out.println("2. Create a room");
        System.out.println("3. Join room");
        System.out.println("4. Delete room");
        System.out.println("5. Type 'exit' to end the program");

        System.out.println("Choose an option:");
        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        mainMenuUI(scan);
                        break;

                    case "2":
                        createRoomUI(scan);
                        break;

                    case "3":
                        joinRoomUI(scan);
                        break;

                    case "4":
                        deleteRoomUI(scan);
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {

        }
    }

    public void joinRoomUI(Scanner scan) throws RemoteException, InterruptedException {
        clearConsole();

        System.out.println("Player: " + Main.playerName);
        System.out.println("========================================");
        System.out.println("Provide token of the room you want to join: ");
        String roomToken = scan.nextLine();
        System.out.println("Provide password of the room you want to join: ");
        String password = scan.nextLine();

        int status = Main.roomService.joinRoom(Main.playerToken, roomToken, password);

        if(status > 0){
            Main.connectedRoomToken = roomToken;
        }

        clearConsole();
        if(status == -2) {
            System.out.println("There is 2 players already. You can only spectate.");
            mainMenuUI(scan);
        }
        else if(status == -1){
            System.out.println("Password is incorrect");
            mainMenuUI(scan);
        }
        else if(status == 0){
            System.out.println("Room with given token doesnt exist");
            mainMenuUI(scan);
        }
        else if(status == 1){
            System.out.println("Joined as a first player!");
            soloRoomUI(scan);
        }
        else if(status == 2){
            System.out.println("Joined as a second player!");
            activeRoomUI(scan);
        }
    }

    public void deleteRoomUI(Scanner scan) throws RemoteException {
        clearConsole();

        System.out.println("Player: " + Main.playerName);
        System.out.println("======================================");
        System.out.println(Main.roomService.listRooms());
        System.out.println();
        System.out.println("Provide token of the room you want to delete: ");
        String roomToken = scan.nextLine();
        System.out.println("Provide password of the room you want to delete: ");
        String password = scan.nextLine();

        int status = Main.roomService.deleteRoom(roomToken, password);

        if(status == 1){
            System.out.println("Room has been deleted");
            mainMenuUI(scan);
        }
        else if(status == -1){
            System.out.println("Room with given token doesnt exist");
            mainMenuUI(scan);
        }
        else if(status == -2){
            System.out.println("Wrong password");
            mainMenuUI(scan);
        }
    }

    public void soloRoomUI(Scanner scan) throws RemoteException, InterruptedException {
        clearConsole();

        System.out.println("Player: " + Main.playerName);
        System.out.println("Room: " + Main.connectedRoomToken);
        System.out.println("=====================================");
        System.out.println();
        System.out.println("Waiting for another player...");

        int iteration = 0;
        int playerNumber = 1;
        while(true){

            playerNumber = Main.roomService.checkPlayersInRoom(Main.connectedRoomToken);
            if(playerNumber >= 2){
                activeRoomUI(scan);
            }

            Thread.sleep(5000);
            if(++iteration % 20 == 0){
                System.out.println("Do you wish to continue waiting? <Y><N>");
                String choice = scan.nextLine();
                if(choice.contains("N"))
                    mainMenuUI(scan);
            }
        }

    }
    public void activeRoomUI(Scanner scan) throws RemoteException {
        clearConsole();

        String opponentName = Main.roomService.getOponent(Main.connectedRoomToken, Main.playerToken);
        boolean isYourTurn = Main.roomService.isYourTurn(Main.playerToken);
        String board = Main.roomService.getBoardInfo(Main.connectedRoomToken);

        System.out.println("Player: " + Main.playerName);
        System.out.println("Room: " + Main.connectedRoomToken);
        System.out.println("=======================================");
        System.out.println("Your opponent: " + opponentName);
        System.out.println();
        System.out.println("=======================================");
        System.out.println(board);
        System.out.println();

        if(isYourTurn){
            System.out.println("Your turn");

            System.out.println("Type number of the row: ");
            int row = scan.nextInt();

            System.out.println("Type number of the column: ");
            int column = scan.nextInt();




        }

    }

    public void clearConsole()
    {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else{
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            for (int i = 0; i < 50; ++i) System.out.println();
        }

    }
}