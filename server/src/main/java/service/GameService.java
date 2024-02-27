package service;

import dataAccess.Auth;
import dataAccess.DataAccessException;
import dataAccess.Game;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    public ArrayList<GameData> listGames(String authToken) throws DataAccessException {
        Auth auth = new Auth();
        Game game = new Game();
        ArrayList<GameData> listGames;
        String authorization = auth.getAuth(authToken);
        listGames = game.listGames();

        return listGames;
    }
    public int createGame(String authToken, String gameName) throws DataAccessException{
        Auth auth = new Auth();
        Game game = new Game();
        int gameID = 0;
        String authorization = auth.getAuth(authToken);

        gameID = game.createGame(gameName);
        return gameID;
    }

    public void joinGame(String authToken, String teamColor, int gameID) throws DataAccessException {
        Auth auth = new Auth();
        Game game = new Game();

        String authorization = auth.getAuth(authToken);
        String username = auth.getUsername(authorization);

        game.updateGame(username,gameID, true, teamColor);

    }

}
