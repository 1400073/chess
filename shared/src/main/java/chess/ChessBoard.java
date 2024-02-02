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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        chess.ChessBoard that = (chess.ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.deepToString(squares) +
                '}';
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
        ChessPiece rookw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        ChessPiece horseb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece horsew = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);

        ChessPiece bishopb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece bishopw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);

        ChessPiece kingb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece kingw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);

        ChessPiece queenb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece queenw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);

        ChessPiece pawnb = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece pawnw = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);


        ChessPosition rookb1 = new ChessPosition(8,8);
        ChessPosition rookb2 = new ChessPosition(8,1);
        ChessPosition rookw1 = new ChessPosition(1,8);
        ChessPosition rookw2 = new ChessPosition(1,1);

        ChessPosition horseb1 = new ChessPosition(8,7);
        ChessPosition horseb2 = new ChessPosition(8,2);
        ChessPosition horsew1 = new ChessPosition(1,7);
        ChessPosition horsew2 = new ChessPosition(1,2);

        ChessPosition bishopb1 = new ChessPosition(8,6);
        ChessPosition bishopb2 = new ChessPosition(8,3);
        ChessPosition bishopw1 = new ChessPosition(1,6);
        ChessPosition bishopw2 = new ChessPosition(1,3);

        ChessPosition kingb1 = new ChessPosition(8,5);
        ChessPosition kingw1 = new ChessPosition(1,5);
        ChessPosition queenb1 = new ChessPosition(8,4);
        ChessPosition queenw1 = new ChessPosition(1,4);

        for(int i =1; i <=8; ++i){
            ChessPosition pawnw1 = new ChessPosition(2,i);
            ChessPosition pawnb1 = new ChessPosition(7,i);
            addPiece(pawnb1,pawnb);
            addPiece(pawnw1,pawnw);
        }
        for(int i = 1; i <=8;++i){
            for(int j = 3; j <=6;++j){
                ChessPosition null1 = new ChessPosition(j,i);
                addPiece(null1,null);
            }
        }
        addPiece(rookb1,rookb);
        addPiece(rookb2,rookb);
        addPiece(rookw1,rookw);
        addPiece(rookw2,rookw);

        addPiece(horseb1,horseb);
        addPiece(horseb2,horseb);
        addPiece(horsew1,horsew);
        addPiece(horsew2,horsew);

        addPiece(bishopb1,bishopb);
        addPiece(bishopb2,bishopb);
        addPiece(bishopw1,bishopw);
        addPiece(bishopw2,bishopw);

        addPiece(kingb1,kingb);
        addPiece(kingw1,kingw);
        addPiece(queenb1,queenb);
        addPiece(queenw1,queenw);
    }
}
