package dataAccess;

import model.GameData;

import java.util.ArrayList;

public interface GameInterface {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    void updateGame(String username, int gameID,boolean join, String teamColor) throws DataAccessException;

    void clear() throws DataAccessException;
}
