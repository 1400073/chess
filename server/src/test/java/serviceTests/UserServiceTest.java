package serviceTests;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void register() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");

        assertAll(() -> userService.register(userData));

    }
    @Test
    void registerThrow() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");
        AuthData authToken = userService.register(userData);
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
        UserData userData = new UserData("normwl","password","");
        assertThrows(DataAccessException.class, () -> userService.login(userData));
    }

    @Test
    void logout() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");
        AuthData authToken = userService.register(userData);
        assertAll(()->userService.logout(authToken.authToken()));

    }
    @Test
    void logoutThrow() throws DataAccessException {
        UserService userService = new UserService();
        assertThrows(DataAccessException.class, () -> userService.logout(""));

    }
}