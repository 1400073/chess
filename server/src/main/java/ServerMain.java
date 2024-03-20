import chess.*;
import server.Server;

public class ServerMain {
    public static void main(String[] args) {

            var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            System.out.println("♕ 240 Chess Server: " + piece);
            Server newTest = new Server();
            newTest.run(8080);


    }
}