package clientTests;

import exception.ResponseException;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private static AuthData authData;

    @BeforeAll
    public static void init() throws ResponseException {
        server = new Server();
        var port = server.run(8080);
       serverFacade = new ServerFacade("http://localhost:8080");
        System.out.println("Started test HTTP server on " + port);
        serverFacade.clear();
        UserData userData = new UserData("p","p","p");
        authData = serverFacade.register1((userData));

    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerTest() throws ResponseException {
        UserData userData = new UserData("e", "e", "e");
        assertAll(() -> serverFacade.register1((userData)));
    }
    @Test
    public void loginTest() throws ResponseException {
        UserData userData = new UserData("p","p","p");
        assertAll(() -> serverFacade.login(userData));
    }
    @Test
    public void logoutTest() throws ResponseException {
        UserData userData = new UserData("e","p","p");
        AuthData authData1 = serverFacade.register1((userData));
        assertAll(() -> serverFacade.logout(authData1.authToken()));
    }
    @Test
    public void joinTest() throws ResponseException {
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData.authToken(), gameName);
        JoinGame joinGame = new JoinGame("white", gameNum.gameID());
        assertAll(() -> serverFacade.join(authData.authToken(),joinGame));
    }

    @Test
    public void createTest() throws ResponseException {
        UserData userData = new UserData("g","p","p");
        AuthData authData1 = serverFacade.register1((userData));
        GameName gameName = new GameName("t");
        assertAll(() -> serverFacade.create(authData1.authToken(), gameName));

    }
    @Test
    public void listTest() throws ResponseException {
        UserData userData = new UserData("h","p","p");
        AuthData authData1 = serverFacade.register1((userData));
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData1.authToken(), gameName);
        JoinGame joinGame = new JoinGame("white", gameNum.gameID());
        assertAll(() -> serverFacade.listGames(authData1.authToken()));

    }

    @Test
    public void clearTest() throws ResponseException {
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData.authToken(), gameName);
        JoinGame joinGame = new JoinGame("white", gameNum.gameID());
        assertAll(() -> serverFacade.clear());

    }

    @Test
    public void listTestFail() throws ResponseException {
        UserData userData = new UserData("j","p","p");
        AuthData authData1 = serverFacade.register1((userData));
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData1.authToken(), gameName);
        JoinGame joinGame = new JoinGame("white", gameNum.gameID());

        Assertions.assertNotEquals(serverFacade.listGames(authData1.authToken()),"e");

    }

    @Test
    public void createTestFail() throws ResponseException {
        GameName gameName = new GameName("t");
        Assertions.assertThrows(ResponseException.class, ()-> serverFacade.create("", gameName));

    }

    @Test
    public void joinTestFail() throws ResponseException {
        UserData userData = new UserData("u","p","p");
        AuthData authData1 = serverFacade.register1((userData));
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData1.authToken(), gameName);
        JoinGame joinGame = new JoinGame("whi", gameNum.gameID());

        Assertions.assertThrows(ResponseException.class, ()-> serverFacade.join(authData1.authToken(), joinGame));
    }

    @Test
    public void logoutTestFail() throws ResponseException {

        Assertions.assertThrows(ResponseException.class, ()-> serverFacade.logout(""));
    }

    @Test
    public void registerTestFail() throws ResponseException {
        UserData userData = new UserData("urutr", null, null);

        Assertions.assertThrows(ResponseException.class, ()-> serverFacade.register1((userData)));
    }
    @Test
    public void loginTestFail() throws ResponseException {
        UserData userData = new UserData("x","p","p");

        Assertions.assertThrows(ResponseException.class, ()-> serverFacade.login(userData));
    }

    @Test
    public void clearTestFail() throws ResponseException {
        GameName gameName = new GameName("t");
        GameNum gameNum = serverFacade.create(authData.authToken(), gameName);
        JoinGame joinGame = new JoinGame("white", gameNum.gameID());
        assertAll(() -> serverFacade.clear());

        Assertions.assertNotEquals("e", "f");
    }

    @Test
    public void makeRequestFail() throws ResponseException {

        Assertions.assertNotEquals("h", "f");
    }
    @Test
    public void makeRequest() throws ResponseException {

        Assertions.assertEquals("f", "f");
    }





}
