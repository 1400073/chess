package dataAccessTests;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserdataAccessTest {
    @Test
void register() {
    UserService userService =new UserService();
    UserData userData = new UserData("nor","password","");
    try{
        AuthData authToken = userService.register(userData);
        userService.logout(authToken.authToken());
        assertAll(() -> userService.login(userData));}
    catch(DataAccessException dataEx){

    }


}
    @Test
    void registerThrow() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");

        assertThrows(DataAccessException.class, () -> userService.register(userData));
    }

    @Test
    void login() {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");
        try{AuthData authToken = userService.register(userData);
            userService.logout(authToken.authToken());
            assertAll(() -> userService.login(userData));}
        catch(DataAccessException dataEx){

        }

    }
    @Test
    void loginThrow() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","passwo","");
        assertThrows(DataAccessException.class, () -> userService.login(userData));
    }

    @Test
    void logout() {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");
        try{AuthData authToken = userService.register(userData);
            assertAll(()->userService.logout(authToken.authToken()));}
        catch(DataAccessException dataEx){

        }

    }
    @Test
    void logoutThrow() throws DataAccessException {
        UserService userService = new UserService();
        assertThrows(DataAccessException.class, () -> userService.logout(""));

    }
}
