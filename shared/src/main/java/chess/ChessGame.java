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

        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> movesValid = piece.pieceMoves(board,startPosition);
        TeamColor teamColor = piece.getTeamColor();
        if(!isInCheck(getTeamTurn())){
            Iterator<ChessMove> iterator2 = movesValid.iterator();
            while(iterator2.hasNext()){
                ChessMove check = iterator2.next();

                ChessPosition getposition1 = check.getStartPosition();
                ChessPosition getposition2 = check.getEndPosition();
                ChessPiece piece2 = board.getPiece(getposition2);
                if(piece2 != null){
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(teamColor)) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                        iterator2.remove();

                    }
                }
                else {
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(teamColor)) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                        iterator2.remove();

                    }
                }
            }

        }
        else if(!isInCheckmate(getTeamTurn())) {
            Iterator<ChessMove> iterator2 = movesValid.iterator();
            while(iterator2.hasNext()){
                ChessMove check = iterator2.next();

                ChessPosition getposition1 = check.getStartPosition();
                ChessPosition getposition2 = check.getEndPosition();
                ChessPiece piece2 = board.getPiece(getposition2);
                if(piece2 != null){
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(getTeamTurn())) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                        iterator2.remove();

                    }
                }
                else {
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(getTeamTurn())) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                        iterator2.remove();

                    }
                }
            }
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
                    if (piece.getTeamColor() == opposeTeam) {
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
        int row = 1;
        int col = 1;
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
    public void arrayCompRe(Collection<ChessMove> moves, Collection<ChessMove> moves2,ChessPosition getPosition){
        ChessPiece value = board.getPiece(getPosition);
        int i = 1;
        if(value.getTeamColor() == ChessGame.TeamColor.BLACK){
            i = -1;
        }
        if (value.getPieceType() == ChessPiece.PieceType.PAWN){
            ChessPosition newPosition1 = new ChessPosition(getPosition.getRow() + i, getPosition.getColumn() + 1);
            ChessPosition newPosition2 = new ChessPosition(getPosition.getRow() + i, getPosition.getColumn() - 1);
            ChessMove move = new ChessMove(getPosition,newPosition1,null);
            ChessMove move2 = new ChessMove(getPosition,newPosition2,null);
            moves.add(move);
            moves.add(move2);
        }

        Iterator<ChessMove> iterator1 = moves2.iterator();

        while(iterator1.hasNext()){
            ChessMove check = iterator1.next();
            for (ChessMove check1 : moves) {
                if (check.getEndPosition().equals(check1.getEndPosition())) {
                    iterator1.remove();
                }
            }
        }

        Iterator<ChessMove> iterator2 = moves2.iterator();
        while(iterator2.hasNext()){
            ChessMove check = iterator2.next();
            ChessPiece piece = board.getPiece(check.getStartPosition());
            ChessPosition getposition1 = check.getStartPosition();
            ChessPosition getposition2 = check.getEndPosition();
            ChessPiece piece2 = board.getPiece(getposition2);
            if(piece2 != null){
                board.addPiece(check.getStartPosition(), null);
                board.addPiece(check.getEndPosition(), piece);
                if (!isInCheck(getTeamTurn())) {
                    board.addPiece(getposition1, piece);
                    board.addPiece(getposition2, piece2);
                } else {
                    board.addPiece(getposition1, piece);
                    board.addPiece(getposition2, piece2);
                    iterator2.remove();

                }
            }
            else {
                board.addPiece(check.getStartPosition(), null);
                board.addPiece(check.getEndPosition(), piece);
                if (!isInCheck(getTeamTurn())) {
                    board.addPiece(getposition1, piece);
                    board.addPiece(getposition2, null);
                } else {
                    board.addPiece(getposition1, piece);
                    board.addPiece(getposition2, null);
                    iterator2.remove();

                }
            }
        }


    }

    public boolean arrayCheckCheck(Collection<ChessMove> moves, ChessPosition checkPos){
        boolean checker = false;
        Iterator<ChessMove> iterator = moves.iterator();
        while(iterator.hasNext()){
            ChessMove check = iterator.next();
            if(check.getEndPosition().equals(checkPos)){
                checker = true;
                ChessPiece piece = board.getPiece(check.getStartPosition());
                ChessPosition getposition1 = check.getStartPosition();
                ChessPosition getposition2 = check.getEndPosition();
                ChessPiece piece2 = board.getPiece(getposition2);
                if(piece2 != null){
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(getTeamTurn())) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, piece2);
                        checker = false;

                    }
                }
                else {
                    board.addPiece(check.getStartPosition(), null);
                    board.addPiece(check.getEndPosition(), piece);
                    if (!isInCheck(getTeamTurn())) {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                    } else {
                        board.addPiece(getposition1, piece);
                        board.addPiece(getposition2, null);
                        checker = false;

                    }
                }
            }
        }
        return checker;
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
           boolean tempCheck = false;
           boolean tempCheck2 = false;

           for(int i = 1; i <= 8; ++i){
               for(int j = 1; j <= 8; ++j){
                   ChessPosition getposition = new ChessPosition(i,j);
                   ChessPiece piece = board.getPiece(getposition);
                   if (piece != null) {
                       if (piece.getTeamColor() == opposeTeam) {
                           Collection<ChessMove> moves1;
                           moves1 = piece.pieceMoves(board,getposition);
                           arrayCompRe(moves1, moves, getposition);

                       }
                       if (piece.getTeamColor() == teamColor) {
                           Collection<ChessMove> moves2;

                           moves2 = piece.pieceMoves(board, getposition);
                           tempCheck= arrayCheckCheck(moves2, squareCheck);
                           tempCheck2 = arrayCheckCheck(moves2, checkPosition);

                       }

                   }
               }
           }
           if(moves.isEmpty()&& !tempCheck && !tempCheck2){
               checkMate = true;
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

        ChessPosition posKing = findKing(board, teamColor);
        ChessPiece piecek = board.getPiece(posKing);
        TeamColor opposeTeam = TeamColor.BLACK;
        if(teamColor == TeamColor.BLACK){
            opposeTeam = TeamColor.WHITE;
        }

        if(!isInCheck(teamColor)){
            Collection<ChessMove> moves;
            moves = piecek.pieceMoves(board, posKing);
            ChessPosition squareCheck = getCheckPosition(posKing);
            boolean tempCheck = false;
            boolean tempCheck2 = false;

            for(int i = 1; i <= 8; ++i){
                for(int j = 1; j <= 8; ++j){
                    ChessPosition getposition = new ChessPosition(i,j);
                    ChessPiece piece = board.getPiece(getposition);
                    if (piece != null) {
                        if (piece.getTeamColor() == opposeTeam) {
                            Collection<ChessMove> moves1;
                            moves1 = piece.pieceMoves(board,getposition);
                            arrayCompRe(moves1,moves, getposition);
                        }
                        if (piece.getTeamColor() == teamColor && piece.getPieceType() != ChessPiece.PieceType.KING) {
                            Collection<ChessMove> moves2;

                            moves2 = piece.pieceMoves(board, getposition);
                            if(!moves2.isEmpty()){
                                tempCheck = false;
                            }
                            else{
                                tempCheck = true;
                            }

                        }


                    }
                }
            }
            if(moves.isEmpty()&& !tempCheck){
                staleMate = true;
            }


        }

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
