package server;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.*;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;

public class ServerFacade {

    private final String serverUrl;
    private AuthData authData;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public AuthData register1(UserData newUser) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, newUser, AuthData.class,"");
    }
    public AuthData login(UserData user) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, user, AuthData.class,"");
    }

    public GameNum create(String authToken, GameName name) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, name, GameNum.class, authToken);
    }
    public void join(String authToken, JoinGame joinGame) throws ResponseException {
        var path = "/game";
        this.makeRequest("PUT", path, joinGame, null, authToken);
    }

    public void logout(String authToken) throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null, authToken);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null,"");
    }

    public ArrayList<GameData> listGames(String authToken) throws ResponseException {
        var path = "/game";
        var response = this.makeRequest("GET", path, null, ListGames.class,authToken);
        return response.games();
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass,String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if(!Objects.equals(authToken, "")) {
                http.addRequestProperty("authorization",authToken);
            }
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}