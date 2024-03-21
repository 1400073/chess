package ui;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessDraw {
    private ChessBoard board = null;
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;

    public ChessDraw(ChessBoard board) {
        this.board = board;
    }
    public void draw(boolean swap){
        var out = new PrintStream(System.out,true);
        out.print(ERASE_SCREEN);

        drawChessBoard(out,swap);

        out.print(SET_BG_COLOR_DARK_GREY);

    }
    private void drawChessBoard(PrintStream out,boolean swap) {



            drawRowOfSquares(out,swap);

    }
    private void drawRowOfSquares(PrintStream out, boolean swap) {
        boolean bgCheck;
        String[] headers = { "a", "b", "c","d","e","f","g","h"};
        String[] numbers = { "1", "2", "3","4","5","6","7","8"};
        String[] headersR = { "h","g","f","e","d","c","b","a"};
        String[] numbersR = { "8","7","6","5","4","3","2","1"};
        for (int squareRow = 0; squareRow <= BOARD_SIZE_IN_SQUARES+1; ++squareRow) {
            for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES+1; ++boardCol) {
                setWhite(out);
                    if(squareRow > 0 && squareRow < 9) {
                        if(boardCol > 0 && boardCol < 9) {

                            if (!checkEven(squareRow)) {
                                bgCheck = true;
                                if (checkEven(boardCol)) {
                                    bgCheck = false;
                                }
                            } else {
                                bgCheck = false;
                                if (checkEven(boardCol)) {
                                    bgCheck = true;
                                }

                            }

                            if (!swap) {
                                ChessPosition position = new ChessPosition(squareRow, boardCol);
                                ChessPiece piece = board.getPiece(position);

                                printSquare(out, bgCheck, piece);
                            }
                            else {ChessPosition position = new ChessPosition(9 - squareRow, 9 - boardCol);
                                ChessPiece piece = board.getPiece(position);

                                printSquare(out, bgCheck, piece);
                                }
                        }
                    }

                    if(squareRow == 0 || squareRow ==9){
                        if(boardCol > 0 && boardCol < 9) {

                            out.print(SET_BG_COLOR_BLACK);

                            out.print(SET_TEXT_COLOR_WHITE);
                            if(!swap) {
                                out.print(headers[boardCol - 1]);
                            }
                            else{
                                out.print(headersR[boardCol - 1]);
                            }
                            out.print("  ");
                        }
                        else{
                            out.print(SET_BG_COLOR_BLACK);
                            out.print("    ");
                        }
                    }
                    if(boardCol == 0 || boardCol == 9){
                        if(squareRow > 0 && squareRow < 9) {
                            out.print(SET_BG_COLOR_BLACK);
                            out.print(" ");
                            out.print(SET_TEXT_COLOR_WHITE);
                            if(!swap) {
                                out.print(numbers[squareRow - 1]);
                            }
                            else{
                                out.print(numbersR[squareRow - 1]);
                            }
                            out.print(" ");
                        }

                    }






            }

            out.println();
        }
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void printSquare(PrintStream out, boolean BGcheck, ChessPiece piece) {
        if(BGcheck){
            out.print(SET_BG_COLOR_LIGHT_GREYISH);
        }
        else{
            out.print(SET_BG_COLOR_DARK_GREY);
        }

        if(piece != null) {
            if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(SET_TEXT_COLOR_DARK_BLUE);
            } else {
                out.print(SET_TEXT_COLOR_RED);
            }
            /*switch (piece.getPieceType()) {
                case ChessPiece.PieceType.PAWN -> out.print(WHITE_PAWN);
                case ChessPiece.PieceType.BISHOP -> out.print(WHITE_BISHOP);
                case ChessPiece.PieceType.ROOK -> out.print(WHITE_ROOK);
                case ChessPiece.PieceType.QUEEN -> out.print(WHITE_QUEEN);
                case ChessPiece.PieceType.KNIGHT -> out.print(WHITE_KNIGHT);
                case ChessPiece.PieceType.KING -> out.print(WHITE_KING);
            }*/
            switch (piece.getPieceType()) {
                case ChessPiece.PieceType.PAWN -> out.print(" P ");
                case ChessPiece.PieceType.BISHOP -> out.print(" B ");
                case ChessPiece.PieceType.ROOK -> out.print(" R ");
                case ChessPiece.PieceType.QUEEN -> out.print(" Q ");
                case ChessPiece.PieceType.KNIGHT -> out.print(" H ");
                case ChessPiece.PieceType.KING -> out.print(" K ");
            }

        }
        else {
            out.print("   ");
        }


    }
    static boolean checkEven(int i){
        return (i % 2) == 0;
    }
}
