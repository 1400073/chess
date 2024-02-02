package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final chess.ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, chess.ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        chess.ChessPiece that = (chess.ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                pieceColor +
                ", "+ type +
                '}'+'\n';
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
    public chess.ChessPiece.PieceType getPieceType() {
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
        ArrayList<ChessMove> moves = new ArrayList<>();
        chess.ChessPiece value = board.getPiece(myPosition);
        chess.ChessPiece.PieceType check = value.getPieceType();
        if (check == chess.ChessPiece.PieceType.BISHOP){
            BishopMoves(board,myPosition,moves);
        }
        if (check == chess.ChessPiece.PieceType.ROOK){
            RookMoves(board,myPosition,moves);
        }
        if (check == chess.ChessPiece.PieceType.PAWN){
            PawnMoves(board,myPosition,moves);
        }
        if (check == chess.ChessPiece.PieceType.QUEEN){
            QueenMoves(board,myPosition,moves);
        }
        if (check == chess.ChessPiece.PieceType.KING){
            KingMoves(board,myPosition,moves);
        }
        if (check == chess.ChessPiece.PieceType.KNIGHT){
            KnightMoves(board,myPosition,moves);
        }
        return moves;
    }
    public void BishopMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves){
        int i = 1;
        int j = 1;
        chess.ChessPiece value = board.getPiece(myPosition);
        while(i <= 8 - myPosition.getRow() && j <= 8 - myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn() + j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(i <= 8 - myPosition.getRow() && j < myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn() - j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(i < myPosition.getRow() && j <= 8-myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()- i, myPosition.getColumn() + j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(i < myPosition.getRow() && j < myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()- i, myPosition.getColumn() - j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
    }
    public void RookMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves){
        int i = 1;
        int j = 1;
        chess.ChessPiece value = board.getPiece(myPosition);
        while(j <= 8 - myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(i <= 8 - myPosition.getRow()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn());
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(i < myPosition.getRow()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()- i, myPosition.getColumn());
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
        i = 1;
        j = 1;
        while(j < myPosition.getColumn()){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - j);
            ChessMove move = new ChessMove(myPosition,newPosition, null);
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if(check1 == null){
                moves.add(move);
            }
            else if (check1.getTeamColor() == value.getTeamColor()){
                break;
            }
            else if(check1.getTeamColor() != value.getTeamColor()) {
                moves.add(move);
                break;
            }

            ++i;
            ++j;
        }
    }
    public void QueenMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves){
        RookMoves(board,myPosition,moves);
        BishopMoves(board,myPosition,moves);
    }
    public void PawnMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves) {
        chess.ChessPiece value = board.getPiece(myPosition);
        int i = 1;
        if(value.getTeamColor() == ChessGame.TeamColor.BLACK){
            i = -1;
        }
        ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn());

        if(newPosition.getRow()<= 8 && newPosition.getRow() > 0 && newPosition.getColumn() <=8 && newPosition.getColumn() >0){
            chess.ChessPiece check1 = board.getPiece(newPosition);
            if (check1 == null) {
                if (newPosition.getRow() == 8|| newPosition.getRow() == 1){
                    ChessMove move = new ChessMove(myPosition,newPosition, chess.ChessPiece.PieceType.QUEEN);
                    moves.add(move);
                    ChessMove move1 = new ChessMove(myPosition,newPosition, chess.ChessPiece.PieceType.BISHOP);
                    moves.add(move1);
                    ChessMove move2 = new ChessMove(myPosition,newPosition, chess.ChessPiece.PieceType.ROOK);
                    moves.add(move2);
                    ChessMove move3 = new ChessMove(myPosition,newPosition, chess.ChessPiece.PieceType.KNIGHT);
                    moves.add(move3);
                }
                else {
                    ChessMove move = new ChessMove(myPosition, newPosition, null);
                    moves.add(move);
                }
            }
        }

        ChessPosition newPosition1 = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn()+1);
        if(newPosition1.getRow()<= 8 && newPosition1.getRow() > 0 && newPosition1.getColumn() <=8 && newPosition1.getColumn() >0) {
            chess.ChessPiece check1 = board.getPiece(newPosition1);
            if (check1 != null) {
                if (check1.getTeamColor() != value.getTeamColor()) {

                    if (newPosition1.getRow() == 8|| newPosition1.getRow() == 1){
                        ChessMove move = new ChessMove(myPosition,newPosition1, chess.ChessPiece.PieceType.QUEEN);
                        moves.add(move);
                        ChessMove move1 = new ChessMove(myPosition,newPosition1, chess.ChessPiece.PieceType.BISHOP);
                        moves.add(move1);
                        ChessMove move2 = new ChessMove(myPosition,newPosition1, chess.ChessPiece.PieceType.ROOK);
                        moves.add(move2);
                        ChessMove move3 = new ChessMove(myPosition,newPosition1, chess.ChessPiece.PieceType.KNIGHT);
                        moves.add(move3);
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, newPosition1, null);
                        moves.add(move);
                    }

                }
            }
        }

        ChessPosition newPosition2 = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn()-1);
        if(newPosition2.getRow()<= 8 && newPosition2.getRow() > 0 && newPosition2.getColumn() <=8 && newPosition2.getColumn() >0) {
            chess.ChessPiece check1 = board.getPiece(newPosition2);
            if (check1 != null) {
                if (check1.getTeamColor() != value.getTeamColor()) {
                    if (newPosition2.getRow() == 8|| newPosition2.getRow() == 1){
                        ChessMove move = new ChessMove(myPosition,newPosition2, chess.ChessPiece.PieceType.QUEEN);
                        moves.add(move);
                        ChessMove move1 = new ChessMove(myPosition,newPosition2, chess.ChessPiece.PieceType.BISHOP);
                        moves.add(move1);
                        ChessMove move2 = new ChessMove(myPosition,newPosition2, chess.ChessPiece.PieceType.ROOK);
                        moves.add(move2);
                        ChessMove move3 = new ChessMove(myPosition,newPosition2, chess.ChessPiece.PieceType.KNIGHT);
                        moves.add(move3);
                    }
                    else {
                        ChessMove move = new ChessMove(myPosition, newPosition2, null);
                        moves.add(move);
                    }

                }
            }
        }

        ChessPosition newPosition3 = new ChessPosition(myPosition.getRow()+ 2, myPosition.getColumn());
        ChessPosition newPosition4 = new ChessPosition(myPosition.getRow()+ 1, myPosition.getColumn());
        if(myPosition.getRow() == 2 && value.getTeamColor() == ChessGame.TeamColor.WHITE) {
            chess.ChessPiece check1 = board.getPiece(newPosition3);
            chess.ChessPiece check2 = board.getPiece(newPosition4);
            if (check1 == null) {
                if (check2 == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition3, null);
                    moves.add(move);
                }
            }
        }

        ChessPosition newPosition5 = new ChessPosition(myPosition.getRow()- 2, myPosition.getColumn());
        ChessPosition newPosition6 = new ChessPosition(myPosition.getRow()- 1, myPosition.getColumn());
        if(myPosition.getRow() == 7 && value.getTeamColor() == ChessGame.TeamColor.BLACK) {
            chess.ChessPiece check1 = board.getPiece(newPosition5);
            chess.ChessPiece check2 = board.getPiece(newPosition6);
            if (check1 == null) {
                if (check2 == null) {
                    ChessMove move = new ChessMove(myPosition, newPosition5, null);
                    moves.add(move);
                }
            }
        }

    }
    public void KingMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves) {
        chess.ChessPiece value = board.getPiece(myPosition);
        for( int i = -1; i <= 1; ++i){
            for(int j = -1; j <=1; ++j){
                ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ i, myPosition.getColumn() + j);
                ChessMove move = new ChessMove(myPosition,newPosition, null);

                if(newPosition.getRow()<= 8 && newPosition.getRow() > 0 && newPosition.getColumn() <=8 && newPosition.getColumn() >0) {
                    chess.ChessPiece check1 = board.getPiece(newPosition);
                    if (check1 == null) {
                        moves.add(move);
                    } else if (check1.getTeamColor() == value.getTeamColor()) {

                    } else if (check1.getTeamColor() != value.getTeamColor()) {
                        moves.add(move);

                    }
                }

            }

        }
    }
    public void KnightMoves(ChessBoard board,ChessPosition myPosition, ArrayList<ChessMove> moves){
        int[] list1= {1,2,-1,-2,-1,-2,1,2};
        int[] list2 ={-2,-1,2,1,-2,-1,2,1};
        chess.ChessPiece value = board.getPiece(myPosition);
        for(int i = 0; i< list1.length;++i){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+ list1[i], myPosition.getColumn() + list2[i]);
            ChessMove move = new ChessMove(myPosition,newPosition, null);

            if(newPosition.getRow()<= 8 && newPosition.getRow() > 0 && newPosition.getColumn() <=8 && newPosition.getColumn() >0) {
                chess.ChessPiece check1 = board.getPiece(newPosition);
                if (check1 == null) {
                    moves.add(move);
                } else if (check1.getTeamColor() == value.getTeamColor()) {

                } else if (check1.getTeamColor() != value.getTeamColor()) {
                    moves.add(move);

                }
            }
        }
    }
}
