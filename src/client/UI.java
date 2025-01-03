package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    public void start() {
        Scanner scan = new Scanner(System.in);
        mainMenuUI(scan);
    }

    public void mainMenuUI(Scanner scan) {

        System.out.println("==============================================");
        System.out.println("Player: " + Main.playerName);
        System.out.println("==============================================");
        System.out.println("Main Menu:");
        System.out.println("1. Create a room");
        System.out.println("2. List all rooms");
        System.out.println("3. Join room");
        System.out.println("4. Delete room");
        System.out.println("5. Watch a game");
        System.out.println("6. Type 'exit' to end the program");

        System.out.println("Choose an option:");
        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        clearConsole();
                        createRoomUI(scan);
                        break;

                    case "2":
                        clearConsole();
                        listAllRoomsUI(scan);
                        break;

                    case "3":
                        clearConsole();
                        joinRoomUI(scan);
                        break;

                    case "4":
                        clearConsole();
                        deleteRoomUI(scan);
                        break;

                    case "5":
                        clearConsole();
                        watchAGameUI(scan);
                        break;

                    default:
                        clearConsole();
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {

        }
    }
    public void createRoomUI(Scanner scan) throws RemoteException {
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
        System.out.println("5. Watch a game");
        System.out.println("6. Type 'exit' to end the program");
        System.out.println("Choose an option:");

        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        clearConsole();
                        mainMenuUI(scan);
                        break;

                    case "2":
                        clearConsole();
                        listAllRoomsUI(scan);
                        break;

                    case "3":
                        clearConsole();
                        joinRoomUI(scan);
                        break;

                    case "4":
                        clearConsole();
                        deleteRoomUI(scan);
                        break;

                    case "5":
                        clearConsole();
                        watchAGameUI(scan);
                        break;

                    default:
                        clearConsole();
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {
        }
    }

    public void listAllRoomsUI(Scanner scan) throws RemoteException {
        System.out.println("Player: " + Main.playerName);
        System.out.println("=====================================");
        System.out.println("Rooms:");
        System.out.println(Main.roomService.listRooms());

        System.out.println("=====================================");
        System.out.println("1. Main Menu");
        System.out.println("2. Create a room");
        System.out.println("3. Join room");
        System.out.println("4. Delete room");
        System.out.println("5. Watch a game");
        System.out.println("6. Type 'exit' to end the program");

        System.out.println("Choose an option:");
        String choice = scan.nextLine();

        try {
            if (choice.equals("exit") || choice.equals("'exit'")) {
                System.exit(0);
            } else {
                switch (choice) {
                    case "1":
                        clearConsole();
                        mainMenuUI(scan);
                        break;

                    case "2":
                        clearConsole();
                        createRoomUI(scan);
                        break;

                    case "3":
                        clearConsole();
                        joinRoomUI(scan);
                        break;

                    case "4":
                        clearConsole();
                        deleteRoomUI(scan);
                        break;

                    case "5":
                        clearConsole();
                        watchAGameUI(scan);
                        break;

                    default:
                        clearConsole();
                        System.out.println("Invalid choice. Try again.");
                        mainMenuUI(scan);
                        break;
                }
            }
        }catch (Exception e) {

        }
    }

    public void joinRoomUI(Scanner scan) throws RemoteException, InterruptedException {
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
            clearConsole();
            System.out.println("There is 2 players already. You can only spectate.");
            mainMenuUI(scan);
        }
        else if(status == -1){
            clearConsole();
            System.out.println("Password is incorrect");
            mainMenuUI(scan);
        }
        else if(status == 0){
            clearConsole();
            System.out.println("Room with given token doesnt exist");
            mainMenuUI(scan);
        }
        else if(status == 1){
            clearConsole();
            System.out.println("Joined as a first player! Your symbol: X");
            soloRoomUI(scan);
        }
        else if(status == 2){
            clearConsole();
            System.out.println("Joined as a second player! Your symbol: O");
            activeRoomUI(scan);
        }
    }

    public void deleteRoomUI(Scanner scan) throws RemoteException {
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
            clearConsole();
            System.out.println("Room has been deleted");
            mainMenuUI(scan);
        }
        else if(status == -1){
            clearConsole();
            System.out.println("Room with given token doesnt exist");
            mainMenuUI(scan);
        }
        else if(status == -2){
            clearConsole();
            System.out.println("Wrong password");
            mainMenuUI(scan);
        }
    }

    public void soloRoomUI(Scanner scan) throws RemoteException, InterruptedException {
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

            Thread.sleep(3000);
            if(++iteration % 15 == 0){
                System.out.println("Do you wish to continue waiting? <Y><N>");
                String choice = scan.nextLine();
                if(choice.contains("N"))
                    mainMenuUI(scan);
            }
        }

    }
    public void activeRoomUI(Scanner scan) throws RemoteException, InterruptedException {
        String opponentName = Main.roomService.getOponent(Main.connectedRoomToken, Main.playerToken);
        boolean isYourTurn = Main.roomService.isYourTurn(Main.connectedRoomToken, Main.playerToken);
        String board = Main.roomService.getBoardInfo(Main.connectedRoomToken);
        String winner = Main.roomService.checkWinner(Main.connectedRoomToken);
        String stats = Main.roomService.getStats(Main.playerToken, Main.connectedRoomToken);

        System.out.println("Stats:");
        System.out.println(stats);
        System.out.println("=======================================");
        System.out.println("Room: " + Main.connectedRoomToken);
        System.out.println("Your opponent: " + opponentName);
        System.out.println();
        System.out.println("=======================================");


        System.out.println(board);
        System.out.println();


        if(!winner.equals("Game ongoing.")){
            if(winner.equals("X") ||  winner.equals("O")){
                System.out.println("Winner's symbol: " + winner);
            }
            else if(winner.equals("D")){
                System.out.println("It's a draw");
            }

            System.out.println("===================================");
            mainMenuUI(scan);
            return;
        }

            if (isYourTurn) {
                System.out.println("Your turn");
                System.out.println("Give number of the row and column:");

                int row, column;
                while (true) {
                    try {
                        System.out.print("Row (1-3): ");
                        row = scan.nextInt() - 1;
                        System.out.print("Column (1-3): ");
                        column = scan.nextInt() - 1;
                        scan.nextLine();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter numbers between 1 and 3.");
                        scan.nextLine();
                    }
                }

                if (Main.roomService.makeMove(Main.connectedRoomToken, Main.playerToken, row, column)) {
                    System.out.println("Move successful!");
                } else {
                    System.out.println("!!!!!!!!Invalid move. Try again.!!!!!!!!!!!!!!!!!");
                    Thread.sleep(1000);
                }


            } else {
                System.out.println("It's not your turn.");
                System.out.println("Wait for the other player to make a move.");

                int iteration = 0;

                while (!Main.roomService.isYourTurn(Main.connectedRoomToken, Main.playerToken)) {
                    Thread.sleep(1000);
                    winner = Main.roomService.checkWinner(Main.connectedRoomToken);
                    if (!winner.equals("Game ongoing.")) {
                        break;
                    }

                    if (++iteration % 100 == 0) {
                        System.out.println("Do you wish to continue waiting? <Y>/<N>");
                        String choice = scan.next().trim().toUpperCase();
                        if (choice.equals("N")) {
                            System.out.println("You left the game");
                            mainMenuUI(scan);
                            return;
                        }
                    }
                }
            }
            activeRoomUI(scan);
    }

    public void watchAGameUI(Scanner scan) throws RemoteException {
        System.out.println("==================================");
        System.out.println("You have entered spectator mode!");
        System.out.println("You can connect and view games between 2 players in chosen room");
        System.out.println(Main.roomService.listRooms());
        System.out.println("Provide token of the room you want to watch: ");
        String roomToken = scan.nextLine();
        Main.connectedRoomToken = roomToken;

        boolean ifExist = Main.roomService.hasRoomWithToken(roomToken);

        if(!ifExist){
            System.out.println("Room with this token doesnt exist.");
            System.out.println("1. Continue");
            System.out.println("2. Type anything else to go back to main menu");
            String choice = scan.nextLine();

            if(choice.equals("1")){
                watchAGameUI(scan);
            }
            else mainMenuUI(scan);
        }

        if(Main.roomService.checkPlayersInRoom(roomToken) != 2){
            System.out.println("You can only enter a room with two players.");
            System.out.println("1. Continue");
            System.out.println("2. Type anything else to go back to main menu");
            String choice = scan.nextLine();

            if(choice.equals("1")){
                watchAGameUI(scan);
            }
            else mainMenuUI(scan);
        }

        try {
            while(true) {
                clearConsole();
                String[] players = Main.socketMessage("getPlayers", Main.connectedRoomToken).split(",");
                String currentPlayer = Main.socketMessage("currentPlayer", Main.connectedRoomToken);
                String board = Main.socketMessage("getBoardInfo", Main.connectedRoomToken);

                System.out.println("====================================");
                System.out.println("Watching");
                System.out.println("Room: " + Main.connectedRoomToken);
                System.out.println("Player number 1: " + players[0]);
                System.out.println("Player number 2: " + players[1]);
                System.out.println();
                System.out.println("Current player: " + currentPlayer);
                System.out.println();
                System.out.println(board.replaceAll("\\*", "\n"));


                String winner = Main.socketMessage("checkWinner", Main.connectedRoomToken);

                if(!winner.equals("Game ongoing.")){
                    if(winner.equals("X")){
                        System.out.println("Winner's symbol: " + winner);
                        mainMenuUI(scan);
                    }
                    else if(winner.equals("O")){
                        System.out.println("Winner's symbol: " + winner);
                        mainMenuUI(scan);
                    }
                    else if(winner.equals("D")){
                        System.out.println("It's a draw");
                        mainMenuUI(scan);
                    }
                    Main.connectedRoomToken = "";
                }
                String turn = currentPlayer;
                int iteration = 0;
                while(turn.equals(currentPlayer)){
                    if(++iteration % 100 == 0){
                        System.out.println("Do you wish to continue waiting? <Y><N>");
                        if(scan.nextLine().contains("N"))
                           mainMenuUI(scan);
                    }
                    Thread.sleep(1000);
                    turn = Main.socketMessage("currentPlayer", roomToken);
                }
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mainMenuUI(scan);
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