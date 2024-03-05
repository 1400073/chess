package dataAccess;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.sql.*;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthAccess implements AuthInterface{
    public MySQLAuthAccess() throws DataAccessException{
        configureDatabase();
    }
    public String generateString(){
        String randStringTake = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        int n = 6;
        StringBuilder newRandString = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(randStringTake.length() * Math.random());

            newRandString.append(randStringTake.charAt(index));
        }

        return newRandString.toString();
    }
    public AuthData createAuth(String username) throws DataAccessException {
        try {
            String randoString = generateString();
            var statement = "INSERT INTO authTokens (username, authToken) VALUES (?, ?)";
            AuthData newAuth = new AuthData(randoString, username);

            executeUpdate(statement, newAuth.username(), newAuth.authToken());
            return newAuth;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("createAuth");
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));

        }

    }
    public String getAuth(String authToken)throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM authTokens WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs).authToken();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("getAutherror");
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));

        }
        return null;
    }
    public void deleteAuth(String authorization)throws DataAccessException{
            var statement = "DELETE FROM authTokens WHERE authToken=?";
            String authToken = authorization;
            String checkAuth =getAuth(authorization);
            if(Objects.equals(checkAuth, null)){
                throw new DataAccessException("unauthorized");

            }
            executeUpdate(statement, authToken);





    }
    public String getUsername(String authToken)throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM authTokens WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs).username();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getUsernameerror");
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }
    private AuthData readAuth(ResultSet rs) throws SQLException {
        try {
            String username = rs.getString("username");
            String authToken = rs.getString("authToken");
            return new AuthData(authToken,username);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("getAutherror");
            throw new SQLException(String.format("Unable to read data: %s", e.getMessage()));

        }
    }
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE TABLE authTokens";
        executeUpdate(statement);
    }
    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];

                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("atbot");
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
            System.out.println("configure");
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authTokens (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
