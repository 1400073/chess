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
        assertAll(() -> userService.login(userData));

    }
    catch(DataAccessException dataEx){
    }


}
    @Test
    void registerThrow() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","","");
        UserData userData2 = new UserData(null,"weirdo","jjj");
        assertThrows(DataAccessException.class, () -> userService.register(userData));
        assertThrows(DataAccessException.class, () -> userService.register(userData2));
    }

    @Test
    void login() {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","password","");
        UserData userData2 = new UserData("normwlbb","passwo","");
        try{

            AuthData authToken = userService.register(userData);
            AuthData authToken2 = userService.register(userData2);
            userService.logout(authToken.authToken());
            assertAll(() -> userService.login(userData));
            assertAll(() -> userService.login(userData2));
        }
        catch(DataAccessException dataEx){

        }

    }
    @Test
    void loginThrow() throws DataAccessException {
        UserService userService =new UserService();
        UserData userData = new UserData("normwl","passwo","");
        assertThrows(DataAccessException.class, () -> userService.login(userData));
        UserData userData2 = new UserData("normwlbb","passwo","");
        try{
            AuthData authToken = userService.register(userData2);
            userService.logout(authToken.authToken());
            assertAll(() -> userService.login(userData2));
        }
        catch(DataAccessException dataEx){

        }
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
