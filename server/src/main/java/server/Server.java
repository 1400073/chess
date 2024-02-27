package server;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.*;
import service.GameService;
import service.UserService;
import service.ClearService;

import spark.*;

import java.util.Map;
import java.util.Objects;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);
        Spark.post("/game", this::createGame);
        Spark.delete("/db", this::clear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request request, Response response) {

        ClearService clear = new ClearService();
        clear.clear();
        return "{}";
    }

    private Object createGame(Request request, Response response) throws DataAccessException {
        var serialzer = new Gson();
        String authToken = request.headers("authorization");
        GameName name = serialzer.fromJson(request.body(), GameName.class);
        GameService gameService = new GameService();
        try{
            GameNum gameID = new GameNum(gameService.createGame(authToken, name.gameName()));
            return new Gson().toJson(gameID);}
        catch (DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            if(Objects.equals(check, "bad request")){
                response.status(400);
            }

            if(Objects.equals(check, "already taken")){
                response.status(403);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }



    }

    private Object joinGame(Request request, Response response) throws DataAccessException {
        var serialzer = new Gson();
        String authToken = request.headers("authorization");
        JoinGame gameRequest = serialzer.fromJson(request.body(), JoinGame.class);
        GameService gameService = new GameService();
        try{gameService.joinGame(authToken,gameRequest.playerColor(), gameRequest.gameID());
            return "{}";
        }
        catch(DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            if(Objects.equals(check, "bad request")){
                response.status(400);
            }

            if(Objects.equals(check, "already taken")){
                response.status(403);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }


    }

    private Object listGames(Request request, Response response) throws DataAccessException{
        String authToken = request.headers("authorization");
        GameService gameService = new GameService();
        try{
            ListGames games = new ListGames(gameService.listGames(authToken));
            return new Gson().toJson(games);
        }
        catch(DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }


    }

    private Object logout(Request request, Response response) throws DataAccessException {
        String authToken = request.headers("authorization");
        UserService userService = new UserService();

        try {userService.logout(authToken);
        return "{}";}
        catch(DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            if(Objects.equals(check, "bad request")){
                response.status(400);
            }

            if(Objects.equals(check, "already taken")){
                response.status(403);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }
    }

    private Object login(Request request, Response response) throws DataAccessException {
        var serialzer = new Gson();
        String authToken = request.headers("authorization");
        UserService userService = new UserService();

        UserData loginRequest = serialzer.fromJson(request.body(), UserData.class);
        try{AuthData auth = userService.login(loginRequest);

        return new Gson().toJson(auth);}
        catch(DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            if(Objects.equals(check, "bad request")){
                response.status(400);
            }

            if(Objects.equals(check, "already taken")){
                response.status(403);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }
    }

    private Object register(Request request, Response response) throws DataAccessException {
        var serialzer = new Gson();
        String authToken = request.headers("authorization");
        UserService userService = new UserService();

        UserData registerRequest = serialzer.fromJson(request.body(), UserData.class);
        try {
            AuthData auth = userService.register(registerRequest);

            return new Gson().toJson(auth);
        }
        catch(DataAccessException dataEx){
            String check = dataEx.getMessage();
            if(Objects.equals(check, "unauthorized")){
                response.status(401);
            }

            if(Objects.equals(check, "bad request")){
                response.status(400);
            }

            if(Objects.equals(check, "already taken")){
                response.status(403);
            }

            return new Gson().toJson(Map.of("message", String.format("Error: %s", dataEx.getMessage())));
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
