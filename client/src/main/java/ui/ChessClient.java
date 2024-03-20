package ui;

import java.util.Arrays;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.*;
import server.ServerFacade;

import static java.lang.Integer.parseInt;

public class ChessClient {
    private final String serverUrl;
    private final ServerFacade server;
    private AuthData authData;
    private State state = State.SIGNEDOUT;
    public ChessClient(String serverUrl) {

        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;

    }




    public String eval(String input) throws ResponseException{
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "list" -> listGames();
                case "logout" -> logout();
                case "observe", "join" -> join(params);
                case "create" -> create(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }
    public String login(String... params) throws ResponseException{
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            UserData user = new UserData(params[0],params[1],"");
            authData = server.login(user);
            return String.format("Logged in as:" + authData.username());
        }
        throw new ResponseException(400, "Expected: <username>");
    }
    public String create(String... params) throws ResponseException{
        if (params.length >= 1) {
            GameName gameName = new GameName(params[0]);
            GameNum num = server.create(authData.authToken(),gameName);
            return String.format("Created " + gameName.gameName() + " gameID: "+ num.gameID());
        }
        throw new ResponseException(400, "Expected: <name>");
    }
    public String register(String... params) throws ResponseException{
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            UserData user = new UserData(params[0],params[1],params[2]);
            authData = server.register1(user);
            return String.format("Logged in as " + authData.username());

        }
        throw new ResponseException(400, "Expected: <username>");
    }
    public String logout(String... params) throws ResponseException{
            assertSignedIn();
            server.logout(authData.authToken());
            authData = new AuthData("","");
            return "Logged out";
    }
    public String listGames(String... params) throws ResponseException {


        var games = server.listGames(authData.authToken());
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }
    public String join(String... params) throws ResponseException{
        if (params.length >= 1) {

            JoinGame joinGame = new JoinGame(params[0], parseInt(params[1]));
            server.join(authData.authToken(), joinGame);
            var games = server.listGames(authData.authToken());
            ChessGame gameGame = new ChessGame();
            for (var game : games) {
                if(joinGame.gameID() == game.gameID()){
                    gameGame = game.game();
                }
            }
            ChessBoard board = gameGame.getBoard();

            return String.format("Joined game as "+ joinGame.playerColor());

        }
        throw new ResponseException(400, "Expected: <username>");
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - login <username> <password> - to start playing
                    - register <username> <password> <email> - creates account
                    - quit
                    - help - get commands
                    """;
        }
        return """
                - list - games
                - create <name> - a game
                - join [WHITE|BLACK|<empty>] <ID> - a game
                - logout - to leave
                - observe <ID> - a game
                - help - get commands
                - quit - playing chess
                """;
    }
    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}
