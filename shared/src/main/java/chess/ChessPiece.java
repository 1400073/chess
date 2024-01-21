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
        if (valuetype == PieceType.QUEEN){
            QueenMoves(board,myPosition,valuetype,moves,value);
        }
        if (valuetype == PieceType.ROOK){
            RookMoves(board, myPosition,valuetype,moves,value);
        }

        return moves;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}'+ '\n';
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
    public void PawnMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                            ArrayList<ChessMove> moves, ChessPiece value) {
        int col = 1;
        if (value.getTeamColor() == ChessGame.TeamColor.BLACK){
            col = -1;
        }

        int upRow = myPosition.getRow() + col;
        int upCol = myPosition.getColumn();
        ChessPosition checkPosition1 = new ChessPosition(upRow,upCol + 1 );
        ChessPosition checkPosition2 = new ChessPosition(upRow, upCol - 1);

        ChessPiece check1 = board.getPiece(checkPosition1);
        ChessPiece check2 = board.getPiece(checkPosition2);

            if (check1 == null) {
                if (check2 == null) {
                    if (upRow <= 8 && upCol < 8 && upRow > 0 && upCol > 0) {
                        ChessPosition newPosition = new ChessPosition(upRow, upCol);
                        ChessPiece check = board.getPiece(newPosition);


                        if (check == null) {
                            ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                            moves.add(move);
                        }


                    }
                }
            }

            else if(check2 == null) {
                if (upRow < 8 && upCol < 8 && upRow > 0 && upCol > 0) {
                    ChessPosition newPosition = new ChessPosition(upRow, upCol);
                    ChessPiece check = board.getPiece(newPosition);


                    if (check == null) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    else{
                        ChessGame.TeamColor teamcheck = check1.getTeamColor();
                        ChessPosition newPosition3 = new ChessPosition(upRow, upCol + 1);
                        if (!teamcheck.equals(value.getTeamColor())) {
                            ChessMove move = new ChessMove(myPosition, newPosition3, valuetype);
                            moves.add(move);
                        }


                    }


                }

            }

            else{
                ChessGame.TeamColor teamcheck = check1.getTeamColor();
                ChessPosition newPosition = new ChessPosition(upRow, upCol + 1);
                if (!teamcheck.equals(value.getTeamColor())) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                }

                ChessGame.TeamColor teamcheck2 = check2.getTeamColor();
                ChessPosition newPosition2 = new ChessPosition(upRow, upCol - 1);
                if (!teamcheck2.equals(value.getTeamColor())) {
                    ChessMove move = new ChessMove(myPosition, newPosition2, valuetype);
                    moves.add(move);
                }
                ChessPosition newPosition3 = new ChessPosition(upRow, upCol);
                ChessPiece check = board.getPiece(newPosition3);


                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition3, valuetype);
                    moves.add(move);
                }

            }

            if (myPosition.getRow() == 7 && value.getTeamColor() == ChessGame.TeamColor.BLACK) {

                ChessPosition newPosition2 = new ChessPosition(upRow + col, upCol);
                ChessPiece check2s = board.getPiece(newPosition2);
                ChessPosition newPosition = new ChessPosition(upRow, upCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check== null) {
                    if (check2s == null) {
                        ChessMove move = new ChessMove(myPosition, newPosition2, valuetype);
                        moves.add(move);
                    }
                }
            }

        if (myPosition.getRow() == 2 && value.getTeamColor() == ChessGame.TeamColor.WHITE) {

            ChessPosition newPosition2 = new ChessPosition(upRow + col, upCol);
            ChessPiece check2s = board.getPiece(newPosition2);
            ChessPosition newPosition = new ChessPosition(upRow, upCol);
            ChessPiece check = board.getPiece(newPosition);
            if (check== null) {
                if (check2s == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition2, valuetype);
                    moves.add(move);
                }
            }
        }




    }

    public void QueenMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                          ArrayList<ChessMove> moves, ChessPiece value) {
        BishopMoves(board, myPosition, valuetype, moves, value);
        RookMoves(board, myPosition, valuetype, moves, value);



    }
    public void RookMoves(ChessBoard board, ChessPosition myPosition, PieceType valuetype,
                            ArrayList<ChessMove> moves, ChessPiece value) {
        int i = 1;

        while(i <= 8 - myPosition.getRow() && i > 0){

            int upRow = myPosition.getRow() + i;
            int upCol = myPosition.getColumn();
            if (upRow <= 8 && upCol <= 8 && upRow > 0 && upCol > 0) {
                ChessPosition newPosition = new ChessPosition(upRow, upCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;

                } else {
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            else{
                break;
            }

        }

        i = 1;

        while(i < myPosition.getRow() && i > 0){
            int downRow = myPosition.getRow() - i;
            int downCol = myPosition.getColumn();

            if (downRow <= 8 && downCol <= 8 && downRow > 0 && downCol > 0) {
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;

                } else {
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            else{
                break;
            }
        }
        i = 1;

        while(i < myPosition.getColumn() && i > 0){
            int downRow = myPosition.getRow();
            int downCol = myPosition.getColumn() - i;
            if (downRow <= 8 && downCol <= 8 && downRow > 0 && downCol > 0) {
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;

                } else {
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            else{
                break;
            }
        }
        i = 1;

        while(i <= 8 - myPosition.getColumn() && i > 0){
            int downRow = myPosition.getRow();
            int downCol = myPosition.getColumn() + i;
            if (downRow <= 8 && downCol <= 8 && downRow > 0 && downCol > 0) {
                ChessPosition newPosition = new ChessPosition(downRow, downCol);
                ChessPiece check = board.getPiece(newPosition);
                if (check == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                    moves.add(move);
                    i++;

                } else {
                    ChessGame.TeamColor teamcheck = check.getTeamColor();
                    if (!teamcheck.equals(value.getTeamColor())) {
                        ChessMove move = new ChessMove(myPosition, newPosition, valuetype);
                        moves.add(move);
                    }
                    break;
                }
            }
            else{
               break;
            }
        }



    }


}
