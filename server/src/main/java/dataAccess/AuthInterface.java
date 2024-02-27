package dataAccess;

import model.AuthData;

public interface AuthInterface {
    AuthData createAuth(String username);

    String getAuth(String authToken)throws DataAccessException;

    AuthData getAuthToken(String username)throws DataAccessException;

    void deleteAuth(String authorization)throws DataAccessException;

    String getUsername(String authToken)throws DataAccessException;


    void clear();
}
