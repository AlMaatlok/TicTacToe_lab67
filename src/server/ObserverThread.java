package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ObserverThread extends Thread {

    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ObserverThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        String message;

        try {
            while (this.clientSocket.isConnected() && (message = reader.readLine()) != null) {
                try {
                    if (message.startsWith("currentPlayer")) {
                        String roomToken = message.split("/")[1];
                        String playerToken = Main.roomService.getPlayerWhosTurn(roomToken);
                        writer.write(playerToken);
                        writer.newLine();
                        writer.flush();
                    } else if (message.startsWith("getPlayers")) {
                        String roomToken = message.split("/")[1];
                        String playerToken1 = Main.roomService.getPlayerWhosTurn(roomToken);
                        String playerToken2 = Main.roomService.getOponent(roomToken, playerToken1);
                        writer.write(playerToken1 + "," + playerToken2);
                        writer.newLine();
                        writer.flush();
                    } else if (message.startsWith("getBoardInfo")) {
                        String roomToken = message.split("/")[1];
                        String boardStatus = Main.roomService.getBoardInfo(roomToken);
                        boardStatus = boardStatus.replaceAll("\\n", "*");

                        writer.write(boardStatus);
                        writer.newLine();
                        writer.flush();
                    } else if (message.startsWith("checkWinner")) {
                        String roomToken = message.split("/")[1];
                        String winner = Main.roomService.checkWinner(roomToken);

                        writer.write(winner);
                        writer.newLine();
                        writer.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + this.clientSocket);
        } finally {
            closeResources();
        }
    }
    private void closeResources() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
