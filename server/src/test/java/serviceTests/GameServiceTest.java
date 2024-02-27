package serviceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.security.authentication.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;
import service.GameService;
import service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void listGamesAssert() throws DataAccessException {
        GameService gameService = new GameService();
        UserService auth =new UserService();
        UserData userData = new UserData("strange","password","");
        AuthData authToken = auth.register(userData);
        int gameID = gameService.createGame(authToken.authToken(),"newGame");
        ArrayList<GameData> check = gameService.listGames(authToken.authToken());
        GameData gameCheck = check.getFirst();
        GameData game = new GameData(gameID,null,null, "newGame", gameCheck.game());
        int i = game.gameID();
        int j = check.getFirst().gameID();
        assertEquals(i,j);
        assertSame(game.whiteUsername(), check.getFirst().whiteUsername());
        assertSame(game.blackUsername(),check.getFirst().blackUsername());

    }

    @Test
    void listGamesThrow() {
        GameService gameService = new GameService();
        Assertions.assertThrows(DataAccessException.class, () -> gameService.listGames("") );

    }

    @Test
    void createGame() throws DataAccessException {
        GameService gameService = new GameService();
        UserService auth =new UserService();
        UserData userData = new UserData("strange","password","");
        AuthData authToken = auth.register(userData);
        int gameID = gameService.createGame(authToken.authToken(),"newGame");
        ArrayList<GameData> check = gameService.listGames(authToken.authToken());
        GameData gameCheck = check.getFirst();
        GameData game = new GameData(gameID,null,null, "newGame", gameCheck.game());
        int i = game.gameID();
        int j = check.getFirst().gameID();
        assertEquals(i,j);
        assertSame(game.whiteUsername(), check.getFirst().whiteUsername());
        assertSame(game.blackUsername(),check.getFirst().blackUsername());
    }
    @Test
    void createGameThrow(){
        GameService gameService = new GameService();
        Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame("",null) );
    }

    @Test
    void joinGame() throws DataAccessException {
        GameService gameService = new GameService();
        UserService auth =new UserService();
        UserData userData = new UserData("noob","password","");
        AuthData authToken = auth.register(userData);
        int gameID = gameService.createGame(authToken.authToken(),"newGame");
        ArrayList<GameData> check = gameService.listGames(authToken.authToken());
        GameData gameCheck = check.getFirst();
        gameService.joinGame(authToken.authToken(),"WHITE",gameID);
        GameData game = new GameData(gameID,"noob",null, "newGame", gameCheck.game());


        assertSame(game.whiteUsername(), check.getFirst().whiteUsername());

    }
    @Test
    void joinGameThrow() throws DataAccessException {
        GameService gameService = new GameService();
        UserService auth =new UserService();
        UserData userData = new UserData("noob","password","");
        AuthData authToken = auth.register(userData);
        int gameID = gameService.createGame(authToken.authToken(),"newGame");
        UserData userData2 = new UserData("noob2","password","");
        AuthData authToken2 = auth.register(userData2);
        gameService.joinGame(authToken.authToken(),"WHITE",gameID);

        Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(authToken2.authToken(),"WHITE",gameID) );
    }

}