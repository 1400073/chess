package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece value = board.getPiece(myPosition);
        PieceType valuetype = value.getPieceType();
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (valuetype == PieceType.BISHOP){
            int i = 1;
            int j = 1;
            while(i <= 8 - myPosition.getRow() && j <= 8 - myPosition.getColumn() ){

                int upRow = myPosition.getRow()+ i;
                int upCol = myPosition.getColumn() + j;
                ChessPosition newPosition = new ChessPosition(upRow, upCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;
                    j++;
                }
                else{
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }

            }

            i = 1;
            j = 1;
            while(i < myPosition.getRow() && j < myPosition.getColumn()){
                int downRow = myPosition.getRow() - i;
                int downCol = myPosition.getColumn() - j;
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;
                    j++;
                }
                else{
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            i = 1;
            j = 1;
            while(i < myPosition.getRow() && j <= 8 - myPosition.getColumn()){
                int downRow = myPosition.getRow() - i;
                int downCol = myPosition.getColumn() + j;
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;
                    j++;
                }
                else{
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            i = 1;
            j = 1;
            while(i <= 8 - myPosition.getRow() && j < myPosition.getColumn()){
                int downRow = myPosition.getRow() + i;
                int downCol = myPosition.getColumn() - j;
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;
                    j++;
                }
                else{
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }

        }
        return moves;
    }
}
