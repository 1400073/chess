package server;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import service.GameService;
import service.UserService;
import service.ClearService;

import spark.*;

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
    record gameName(String gameName){}
    private Object clear(Request request, Response response) {


    }

    private Object createGame(Request request, Response response) throws DataAccessException {
        var serialzer = new Gson();
        String auth = request.headers("authorization");
        gameName name = serialzer.fromJson(request.body(), gameName.class);
        GameService gameService = new GameService();
        int gameID = gameService.CreateGame(auth, name.gameName());
        return new Gson().toJson(gameID);
    }

    private Object joinGame(Request request, Response response) {

        
    }

    private Object listGames(Request request, Response response) {
    }

    private void logout(Request request, Response response) {

    }

    private Object login(Request request, Response response) {

    }

    private Object register(Request req, Response res){

    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
