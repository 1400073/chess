package dataAccess;


import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Objects;

public class Game implements GameInterface{
    private final static ArrayList<GameData> games = new ArrayList<>();

    public int generateNum(){
        String randNumTake = "0123456789";
        int returnNum;
        int n = 4;
        StringBuilder newNumString = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(randNumTake.length() * Math.random());

            newNumString.append(randNumTake.charAt(index));
        }

        returnNum = Integer.parseInt(newNumString.toString());
        return returnNum;
    }
    @Override
    public int createGame(String gameName) {
        ChessGame game = new ChessGame();
        int gameID = generateNum();

        while(checkGameID(gameID)){
            gameID = generateNum();
        }

        GameData newGame = new GameData(gameID, null, null,gameName, game);
        games.add(newGame);
        return gameID;
    }

    public boolean checkGameID(int gameID){
        boolean check = false;
        int i =0;

        if(games!= null) {
            while (i < games.size()) {
                GameData game = games.get(i);
                if (game.gameID() == gameID) {
                    check = true;
                }
                i += 1;
            }
        }
        return check;
    }
    public GameData getGame(int gameID) throws DataAccessException {
        int i =0;
        GameData game = null;
        if(!games.isEmpty()) {
            while (i < games.size()) {

                if (games.get(i).gameID() == gameID) {
                    game = games.get(i);
                }
                i += 1;
            }
        }

        if (game == null) {
            throw new DataAccessException("bad request");
        }

        return game;
    }

    public int getGameNum(int gameID) throws DataAccessException {
        int i =0;
        int findNum = 0;
        boolean foundNum = false;
        if(!games.isEmpty()) {
            while (i < games.size()) {

                if (games.get(i).gameID() == gameID) {
                    findNum = i;
                    foundNum = true;

                }
                i += 1;
            }
        }

        if (!foundNum) {
            throw new DataAccessException("bad request");
        }

        return findNum;
    }


    public ArrayList<GameData> listGames(){
        return games;
    }

    public void updateGame(String username, int gameID, boolean join, String teamColor) throws DataAccessException {
        if(!Objects.equals(username, "")){
            GameData game = getGame(gameID);
            int gameNum = getGameNum(gameID);
            if(join){
                if(Objects.equals(teamColor, "BLACK")){
                    if(Objects.equals(game.blackUsername(), null)){
                        String whiteUsername = game.whiteUsername();
                        ChessGame game1 = game.game();
                        String gameName = game.gameName();
                        games.set(gameNum, new GameData(gameID, whiteUsername, username, gameName, game1));

                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
                }

                else if(Objects.equals(teamColor, "WHITE")){
                    if(Objects.equals(game.whiteUsername(), null)){
                        String blackUsername = game.blackUsername();
                        ChessGame game1 = game.game();
                        String gameName = game.gameName();
                        games.set(gameNum, new GameData(gameID, username, blackUsername, gameName, game1));
                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
                }
                else if(Objects.equals(teamColor, null)){

                }
                else{
                    throw new DataAccessException("bad request");
                }

            }
            else{

            }
        }

    }
    public void clear(){

        if(!games.isEmpty()){
            games.clear();
        }
    }

}
