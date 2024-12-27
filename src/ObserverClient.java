public class ObserverClient {

    public void update(String message, String boardInfo) {
        if(message == "reset"){
            System.out.println("Game is being reset.\\ New game starting...");
        }
        else if(message == "move"){
            System.out.println("New move by a player.");
        }
        else if(message == "winX"){
            System.out.println("Player X won!!");
        }
        else if(message == "winO"){
            System.out.println("Player O won!!");
        }
        else if(message == "draw"){
            System.out.println("Draw! No winner.");
        }
        System.out.println(boardInfo);
    }
}
