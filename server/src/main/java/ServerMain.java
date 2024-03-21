import chess.*;
import server.Server;

public class ServerMain {
    public static void main(String[] args) {


            Server newTest = new Server();
            newTest.run(8080);


    }
}