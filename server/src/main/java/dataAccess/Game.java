package dataAccess;


import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Objects;

public class Game implements GameInterface{
    private ArrayList<GameData> games;

    public int GenerateNum(){
        String RandNumTake = "0123456789";
        int returnNum;
        int n = 4;
        StringBuilder newNumString = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(RandNumTake.length() * Math.random());

            newNumString.append(RandNumTake.charAt(index));
        }

        returnNum = Integer.parseInt(newNumString.toString());
        return returnNum;
    }
    @Override
    public int createGame(String gameName) {
        ChessGame game = new ChessGame();
        int gameID = GenerateNum();

        while(checkGameID(gameID)){
            gameID = GenerateNum();
        }

        GameData newGame = new GameData(gameID, "", "",gameName, game);
        games.add(newGame);
        return gameID;
    }

    public boolean checkGameID(int gameID){
        boolean check = false;
        int i =0;
        while(i < games.size()){
            GameData game = games.get(i);
            if(game.gameID()== gameID){
                check= true;
            }
            i +=1;
        }
        return check;
    }
    public GameData getGame(int gameID) throws DataAccessException {
        int i =0;
        while(i < games.size()){
            GameData game = games.get(i);
            if(game.gameID()== gameID){
                return game;
            }
            i +=1;
        }

        throw new DataAccessException("bad request");
    }



    public ArrayList<GameData> listGames(){
        return games;
    }

    public void updateGame(String username, int gameID,boolean join, String teamColor) throws DataAccessException {
        if(!Objects.equals(username, "")){
            GameData game = getGame(gameID);
            if(join){
                if(Objects.equals(teamColor, "BLACK")){
                    if(Objects.equals(game.blackUsername(), "")){
                        String whiteUsername = game.whiteUsername();
                        ChessGame game1 = game.game();
                        String gameName = game.gameName();
                        game = new GameData(gameID,whiteUsername, username, gameName, game1);

                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
                }

                else if(Objects.equals(teamColor, "WHITE")){
                    if(Objects.equals(game.whiteUsername(), "")){
                        String blackUsername = game.blackUsername();
                        ChessGame game1 = game.game();
                        String gameName = game.gameName();
                        game = new GameData(gameID,username, blackUsername, gameName, game1);
                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
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
        games.clear();
    }

}
