package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor chessTurn = TeamColor.BLACK;
    chess.ChessBoard board = new ChessBoard();

    ChessPiece checkPiece;
    ChessPosition checkPosition;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return chessTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        chessTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> movesValid = new ArrayList<>();
        ChessPiece piece = board.getPiece(startPosition);
        if(!isInCheck(getTeamTurn()) && !isInStalemate(getTeamTurn())){
            if(piece.getPieceType() != ChessPiece.PieceType.KING ){

            }
        }
        else if(!isInCheckmate(getTeamTurn())) {

        }

        return movesValid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPiece piece = board.getPiece(move.getStartPosition());
        ChessPosition getposition1 = move.getStartPosition();
        ChessPosition getposition2 = move.getEndPosition();
        boolean checker = false;
        if(piece.getTeamColor() == getTeamTurn()) {
            Collection<ChessMove> moves;
            moves = piece.pieceMoves(board, getposition1);
            checker = arrayCheck(moves, getposition2);
            if (checker) {
                if(piece.getPieceType() != ChessPiece.PieceType.PAWN || getposition2.getRow() != 8 && getposition2.getRow() != 1) {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), piece);
                    if (!isInCheck(getTeamTurn())) {
                        if (getTeamTurn() == TeamColor.BLACK) {
                            setTeamTurn(TeamColor.WHITE);
                        } else {
                            setTeamTurn(TeamColor.BLACK);
                        }
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                        throw new InvalidMoveException();
                    }
                }
                else{
                    board.addPiece(move.getStartPosition(),null);
                    ChessPiece newPiece = new ChessPiece(getTeamTurn(),move.getPromotionPiece());
                    board.addPiece(move.getEndPosition(), newPiece);
                    if (!isInCheck(getTeamTurn())) {
                        if (getTeamTurn() == TeamColor.BLACK) {
                            setTeamTurn(TeamColor.WHITE);
                        } else {
                            setTeamTurn(TeamColor.BLACK);
                        }
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                        throw new InvalidMoveException();
                    }
                }
            }
            else{
                throw new InvalidMoveException();
            }
        }
        else{
            throw new InvalidMoveException();
        }


    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean checkCheck = false;
        TeamColor opposeTeam = TeamColor.BLACK;
        if(teamColor == TeamColor.BLACK){
            opposeTeam = TeamColor.WHITE;
        }
        ChessPosition checkPos = findKing(board,teamColor);

        for(int i = 1; i <= 8; ++i){
            for(int j = 1; j <= 8; ++j){
                ChessPosition getposition = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(getposition);
                if (piece != null) {
                    if (piece.getTeamColor() == opposeTeam && piece.getPieceType() != ChessPiece.PieceType.KING) {
                        Collection<ChessMove> moves;
                        boolean tempCheck = false;
                        moves = piece.pieceMoves(board, getposition);
                        tempCheck= arrayCheck(moves, checkPos);
                        if(tempCheck){
                            checkCheck = true;
                            checkPiece = piece;
                            checkPosition = getposition;

                        }
                    }
                }
            }
        }
        return checkCheck;
    }
    public ChessPosition findKing(ChessBoard board, TeamColor checkTeam){
        int row = 0;
        int col = 0;
        for(int i = 1; i <= 8; ++i){
            for(int j = 1; j <= 8; ++j){
                ChessPosition getposition = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(getposition);
                if(piece != null) {
                    if (piece.getTeamColor() == checkTeam && piece.getPieceType() == ChessPiece.PieceType.KING) {
                        row = getposition.getRow();

                        col = getposition.getColumn();
                    }
                }
            }
        }
        ChessPosition posKing = new ChessPosition(row,col);
        return posKing;
    }
    public boolean arrayCheck(Collection<ChessMove> moves, ChessPosition checkPos){
        boolean checker = false;
        Iterator<ChessMove> iterator = moves.iterator();
        while(iterator.hasNext()){
            ChessMove check = iterator.next();
            if(check.getEndPosition().equals(checkPos)){
                checker = true;
            }
        }
        return checker;
    }
    public void arrayCompRe(Collection<ChessMove> moves, Collection<ChessMove> moves2){

        Iterator<ChessMove> iterator = moves.iterator();
        Iterator<ChessMove> iterator1 = moves2.iterator();

        while(iterator.hasNext()){
            ChessMove check = iterator.next();
            while(iterator1.hasNext()) {
                ChessMove check1 = iterator1.next();
                if (check.equals(check1)) {
                    iterator1.remove();
                }
            }
        }

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
       boolean checkMate = false;

       ChessPosition posKing = findKing(board, teamColor);
       ChessPiece piecek = board.getPiece(posKing);
       TeamColor opposeTeam = TeamColor.BLACK;
       if(teamColor == TeamColor.BLACK){
           opposeTeam = TeamColor.WHITE;
       }

       if(isInCheck(teamColor)){
           Collection<ChessMove> moves;
           moves = piecek.pieceMoves(board, posKing);
           ChessPosition squareCheck = getCheckPosition(posKing);

           for(int i = 1; i <= 8; ++i){
               for(int j = 1; j <= 8; ++j){
                   ChessPosition getposition = new ChessPosition(i,j);
                   ChessPiece piece = board.getPiece(getposition);
                   if (piece != null) {
                       if (piece.getTeamColor() == opposeTeam && piece.getPieceType() != ChessPiece.PieceType.KING) {
                           Collection<ChessMove> moves1;
                           moves1 = piece.pieceMoves(board,getposition);
                           arrayCompRe(moves1,moves);
                       }
                       
                   }
               }
           }
           if(moves.isEmpty()){


               for(int i = 1; i <= 8; ++i){
                   for(int j = 1; j <= 8; ++j){
                       ChessPosition getposition = new ChessPosition(i,j);
                       ChessPiece piece = board.getPiece(getposition);
                       if (piece != null) {
                           if (piece.getTeamColor() == opposeTeam && piece.getPieceType() != ChessPiece.PieceType.KING) {
                               Collection<ChessMove> moves2;
                               boolean tempCheck = false;
                               moves2 = piece.pieceMoves(board, getposition);
                               tempCheck= arrayCheck(moves2, squareCheck);

                           }
                       }
                   }
               }

           }


       }

       return checkMate;
    }
public ChessPosition getCheckPosition(ChessPosition posKing){
        ChessPosition squareCheck = new ChessPosition(0,0);

    if(checkPiece!= null){
        if(checkPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
            if(checkPosition.getColumn()<posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() - 1, posKing.getColumn()-1);
            }
            if(checkPosition.getColumn()> posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() -1, posKing.getColumn()+1 );
            }
            if(checkPosition.getColumn()> posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() + 1, posKing.getColumn()+1 );
            }
            if(checkPosition.getColumn()< posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() +1, posKing.getColumn()-1 );
            }
            if(checkPosition.getColumn()==posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() +1, posKing.getColumn() );
            }
            if(checkPosition.getColumn()==posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() -1, posKing.getColumn() );
            }
            if(checkPosition.getColumn()>posKing.getColumn() && checkPosition.getRow()== posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow(), posKing.getColumn() + 1 );
            }
            if(checkPosition.getColumn()<posKing.getColumn() && checkPosition.getRow()== posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow(), posKing.getColumn() - 1 );
            }
        }
        if(checkPiece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            squareCheck = checkPosition;
        }
        if(checkPiece.getPieceType() == ChessPiece.PieceType.PAWN){
            squareCheck = checkPosition;
        }
        if(checkPiece.getPieceType() == ChessPiece.PieceType.BISHOP){
            if(checkPosition.getColumn()<posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() - 1, posKing.getColumn()-1);
            }
            if(checkPosition.getColumn()> posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() -1, posKing.getColumn()+1 );
            }
            if(checkPosition.getColumn()> posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() + 1, posKing.getColumn()+1 );
            }
            if(checkPosition.getColumn()< posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() +1, posKing.getColumn()-1 );
            }
        }
        if(checkPiece.getPieceType() == ChessPiece.PieceType.ROOK){
            if(checkPosition.getColumn()==posKing.getColumn() && checkPosition.getRow()> posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() +1, posKing.getColumn() );
            }
            if(checkPosition.getColumn()==posKing.getColumn() && checkPosition.getRow()< posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow() -1, posKing.getColumn() );
            }
            if(checkPosition.getColumn()>posKing.getColumn() && checkPosition.getRow()== posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow(), posKing.getColumn() + 1 );
            }
            if(checkPosition.getColumn()<posKing.getColumn() && checkPosition.getRow()== posKing.getRow()){
                squareCheck = new ChessPosition(posKing.getRow(), posKing.getColumn() - 1 );
            }
        }

    }

        return squareCheck;
}
    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean staleMate = false;

        return staleMate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
