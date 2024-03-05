package service;

import dataAccess.*;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    public ArrayList<GameData> listGames(String authToken) throws DataAccessException {
        MySQLAuthAccess auth = new MySQLAuthAccess();

        MySQLGameAccess game = new MySQLGameAccess();
        ArrayList<GameData> listGames;
        String authorization = auth.getAuth(authToken);
        if(authorization == null){
            throw new DataAccessException("unauthorized");
        }
        listGames = game.listGames();

        return listGames;
    }
    public int createGame(String authToken, String gameName) throws DataAccessException{
        MySQLAuthAccess auth = new MySQLAuthAccess();
        MySQLGameAccess game = new MySQLGameAccess();
        int gameID = 0;
        String authorization = auth.getAuth(authToken);
        if(authorization == null){
            throw new DataAccessException("unauthorized");
        }
        gameID = game.createGame(gameName);
        return gameID;
    }

    public void joinGame(String authToken, String teamColor, int gameID) throws DataAccessException {
        MySQLAuthAccess auth = new MySQLAuthAccess();

        MySQLGameAccess game = new MySQLGameAccess();

        String authorization = auth.getAuth(authToken);
        if(authorization == null){
            throw new DataAccessException("unauthorized");
        }
        String username = auth.getUsername(authorization);

        game.updateGame(username,gameID, true, teamColor);

    }

}
