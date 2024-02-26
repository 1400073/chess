package dataAccess;

import model.UserData;

public interface UserInterface {
    void createUser(String username, String password, String email);

    UserData getUser(String username)throws DataAccessException;

    void clear();
}
