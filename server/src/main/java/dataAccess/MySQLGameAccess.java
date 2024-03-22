package dataAccess;
import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameAccess implements GameInterface{

    public MySQLGameAccess() throws DataAccessException{
        configureDatabase();
    }
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

    public int createGame(String gameName) throws DataAccessException {
        int randoInt = generateNum();
        ChessGame chessGame = new ChessGame();
        chessGame.getBoard().resetBoard();
        var statement = "INSERT INTO games (gameID, gameName, game) VALUES (?,?,?)";
        var json1 = new Gson().toJson(chessGame);
        executeUpdate(statement, randoInt, gameName, json1);
        return randoInt;

    }
    public GameData getGame(int gameID)throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,game FROM games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1,gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("bad request");
        }

        return null;
    }
    public ArrayList<GameData> listGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID,whiteUsername,blackUsername,gameName,game FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public void updateGame(String username, int gameID,boolean join, String teamColor) throws DataAccessException{
        if(!Objects.equals(username, "")){
            GameData game = getGame(gameID);
            if(game == null){
                throw new DataAccessException("bad request");
            }
            if(join){
                if(Objects.equals(teamColor, "black")){
                    if(Objects.equals(game.blackUsername(), null)){
                        String statement = "UPDATE games SET blackUsername = ? WHERE gameID = ?";
                        executeUpdate(statement, username,gameID);

                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
                }

                else if(Objects.equals(teamColor, "white")){
                    if(Objects.equals(game.whiteUsername(), null)){
                        String statement = "UPDATE games SET whiteUsername = ? WHERE gameID = ?";
                        executeUpdate(statement, username,gameID);
                    }
                    else{
                        throw new DataAccessException("already taken");
                    }
                }
                else if(Objects.equals(teamColor, "")){

                }
                else{
                    throw new DataAccessException("bad request");
                }

            }
            else{

            }
        }
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        String gameName = rs.getString("gameName");
        int gameID = rs.getInt("gameID");
        String blackUsername = rs.getString("blackUsername");
        String whiteUsername = rs.getString("whiteUsername");
        ChessGame game = new Gson().fromJson(rs.getString("game"), ChessGame.class);

        return new GameData(gameID,whiteUsername,blackUsername,gameName,game);
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }
    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    rs.getInt(1);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private void configureDatabase()throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` LONGTEXT NOT NULL,
                PRIMARY KEY (`gameID`),
              INDEX(gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

}
