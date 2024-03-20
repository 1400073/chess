package ui;
import chess.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ChessDraw {
    private final ChessBoard board;

    public ChessDraw(ChessBoard board) {
        this.board = board;
    }
    public static void draw(){
        var out = new PrintStream(System.out,true);
    }

}
