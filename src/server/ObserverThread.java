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

        while(this.clientSocket.isConnected()) {
            try{
                message = reader.readLine();

                if(message.startsWith("currentPlayer")){
                    String roomToken = message.split(":")[1];
                    String playerToken = Main.roomService.getPlayerWhosTurn(roomToken);
                    writer.write(playerToken);
                    writer.newLine();
                    writer.flush();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
