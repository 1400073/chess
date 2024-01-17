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
        if (valuetype == PieceType.BISHOP) {
            BishopMoves(board,myPosition,valuetype,moves,value);
        }
        if (valuetype == PieceType.KING){
            KingMoves(board,myPosition,valuetype,moves,value);
        }
        if (valuetype == PieceType.KNIGHT){
            KnightMoves(board,myPosition,valuetype,moves,value);
        }
        if (valuetype == PieceType.PAWN){
            PawnMoves(board,myPosition,valuetype,moves,value);
        }

        return moves;
    }





    public void BishopMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                                             ArrayList<ChessMove> moves, ChessPiece value) {
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


    public void KingMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                            ArrayList<ChessMove> moves, ChessPiece value) {

        for(int i = -1; i <= 1; ++i ) {
            for(int j = -1; j <= 1; ++j ) {

                int upRow = myPosition.getRow() + i;
                int upCol = myPosition.getColumn() + j;
                if (upRow <= 8 && upCol <= 8 && upRow > 0 && upCol > 0) {
                    ChessPosition newPosition = new ChessPosition(upRow, upCol);
                    ChessPiece check = board.getPiece(newPosition);
                    if (check == null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    } else {
                        ChessGame.TeamColor teamcheck = check.getTeamColor();
                        if (!teamcheck.equals(value.getTeamColor())) {
                            ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                            moves.add(move);
                        }

                    }
                }
            }

        }




    }


    public void KnightMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                          ArrayList<ChessMove> moves, ChessPiece value) {
        int[] numHorse = new int[]{2,-2, 1,-1,-1,-2, 1, 2};
        int[] numHorse2 = new int[]{1,-1, 2,-2, 2, 1,-2,-1};

        for(int i = 0; i < 8; ++i ) {


                int upRow = myPosition.getRow() + numHorse[i];
                int upCol = myPosition.getColumn() + numHorse2[i];

                if (upRow <= 8 && upCol <= 8 && upRow > 0 && upCol > 0) {
                    ChessPosition newPosition = new ChessPosition(upRow, upCol);
                    ChessPiece check = board.getPiece(newPosition);
                    if (check == null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    } else {
                        ChessGame.TeamColor teamcheck = check.getTeamColor();
                        if (!teamcheck.equals(value.getTeamColor())) {
                            ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                            moves.add(move);
                        }

                    }
                }


        }




    }

}
