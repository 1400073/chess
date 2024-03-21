package clientTests;
import chess.ChessBoard;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ChessDraw;

public class ChessDrawTests {
    private static ChessBoard board = new ChessBoard();
    @BeforeAll
    public static void init() {
        board.resetBoard();
    }

    public static void setBoard(ChessBoard board) {
        ChessDrawTests.board = board;
    }

    @Test
    public void testDraw() {
        ChessDraw chessDraw = new ChessDraw(board);
        chessDraw.draw(true);

    }
}
