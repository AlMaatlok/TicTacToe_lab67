import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RoomServer {
    private SessionManager sessionManager;
    private Logger logger;

    public RoomServer(SessionManager sessionManager, Logger logger) {
        this.sessionManager = sessionManager;
        this.logger = logger;      //do poprawy
    }

    public void startSever(){
        try{
            RoomServiceImpl roomService = new RoomServiceImpl(sessionManager);
            RoomServiceImpl stub = (RoomServiceImpl) UnicastRemoteObject.exportObject(roomService, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("RoomService", stub);

        }
        catch(Exception e){
        }
    }

    public void logInfo(String info){}
}
