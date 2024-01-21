package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;

    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        ChessPiece rookb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece horseb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece bishopb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece queenb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece kingb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);

        ChessPiece rookw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece horsew = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece bishopw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece queenw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece kingw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);

        ChessPiece pawnb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece pawnw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        ChessPosition rookb1 = new ChessPosition(1,1);
        ChessPosition horseb1 = new ChessPosition(1,2);
        ChessPosition bishopb1 = new ChessPosition(1, 3);
        ChessPosition queenbb = new ChessPosition(1,4);
        ChessPosition kingbb = new ChessPosition(1,5);
        ChessPosition bishopb2 = new ChessPosition(1,6);
        ChessPosition horseb2 = new ChessPosition(1,7);
        ChessPosition rookb2 = new ChessPosition(1,8);

        ChessPosition rookw1 = new ChessPosition(8,1);
        ChessPosition horsew1 = new ChessPosition(8,2);
        ChessPosition bishopw1 = new ChessPosition(8, 3);
        ChessPosition queenww = new ChessPosition(8,4);
        ChessPosition kingww = new ChessPosition(8, 5);
        ChessPosition bishopw2 = new ChessPosition(8,6);
        ChessPosition horsew2 = new ChessPosition(8,7);
        ChessPosition rookw2 = new ChessPosition(8,8);

        for (int i = 1; i <= 8; ++i){
            ChessPosition pawnPosib = new ChessPosition(7,i);
            ChessPosition pawnPosiw = new ChessPosition(2, i);
            addPiece(pawnPosib, pawnb);
            addPiece(pawnPosiw, pawnw);
        }

        for(int i = 2; i <= 5; ++i){
            for (int j = 0; j < 8; ++j){
                squares[i][j] = null;
            }
        }

        addPiece(rookb1,rookb);
        addPiece(rookb2,rookb);
        addPiece(horseb1,horseb);
        addPiece(horseb2,horseb);
        addPiece(bishopb1,bishopb);
        addPiece(bishopb2,bishopb);
        addPiece(queenbb,queenb);
        addPiece(kingbb,kingb);

        addPiece(rookw1,rookw);
        addPiece(rookw2,rookw);
        addPiece(horsew1,horsew);
        addPiece(horsew2,horsew);
        addPiece(bishopw1,bishopw);
        addPiece(bishopw2,bishopw);
        addPiece(queenww,queenw);
        addPiece(kingww,kingw);

    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }
}
