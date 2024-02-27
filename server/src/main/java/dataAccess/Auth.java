package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Objects;

public class Auth implements AuthInterface{
    private static final ArrayList<AuthData> authTokens = new ArrayList<>();

    public String generateString(){
        String randStringTake = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        int n = 6;
        StringBuilder newRandString = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(randStringTake.length() * Math.random());

            newRandString.append(randStringTake.charAt(index));
        }

        return newRandString.toString();
    }

    public AuthData createAuth(String username){
            String authTokenTemp =  generateString();
            AuthData authDataTemp = new AuthData(authTokenTemp, username);

            authTokens.add(authDataTemp);


            return authDataTemp;
    }

    public String getAuth(String authToken)throws DataAccessException{
        int i = 0;
        String authorization = "";
        while(i < authTokens.size()){
            if(Objects.equals(authTokens.get(i).authToken(), authToken)){
                authorization = authTokens.get(i).authToken();
            }
            i+=1;

        }
        if (authorization.isEmpty()) {
            throw new DataAccessException("unauthorized");
        }
        return authorization;
    }

    public String getUsername(String authToken)throws DataAccessException{
        int i = 0;
        String username = "";
        while(i < authTokens.size()){
            if(Objects.equals(authTokens.get(i).authToken(), authToken)){
                username = authTokens.get(i).username();
            }
            i+=1;

        }
        if (username.isEmpty()) {
            throw new DataAccessException("unauthorized");
        }
        return username;
    }


    public void deleteAuth(String authorization)throws DataAccessException{
        boolean deleteSucees = false;
        int i = 0;
        while(i < authTokens.size()){
            if(Objects.equals(authTokens.get(i).authToken(), authorization)){
                authTokens.remove(i);
                deleteSucees = true;
            }
            i+=1;

        }
        if(!deleteSucees){
            throw new DataAccessException("unauthorized");
        }
    }
    public void clear(){
        if(!authTokens.isEmpty()) {
            authTokens.clear();
        }
    }

}
