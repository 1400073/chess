package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Objects;

public class Auth implements AuthInterface{
    private ArrayList<AuthData> authTokens;
    private ArrayList<String> authorizations;

    public String GenerateString(){
        String RandStringTake = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        int n = 6;
        StringBuilder newRandString = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(RandStringTake.length() * Math.random());

            newRandString.append(RandStringTake.charAt(index));
        }

        return newRandString.toString();
    }

    public AuthData createAuth(String username){
            String authTokenTemp =  GenerateString();
            AuthData authDataTemp = new AuthData(authTokenTemp, username);

            authTokens.add(authDataTemp);
            authorizations.add(authDataTemp.authToken());

            return authDataTemp;
    }

    public String getAuth(String authToken)throws DataAccessException{
        int i = 0;
        String authorization = "";
        while(i < authorizations.size()){
            if(Objects.equals(authorizations.get(i), authToken)){
                authorization = authorizations.get(i);
            }
            i+=1;

        }
        if (authorization.isEmpty()) {
            throw new DataAccessException("unauthorized");
        }
        return authorization;
    }

    public AuthData getAuthToken(String username)throws DataAccessException{
        int i = 0;
        AuthData authToken = new AuthData(null,null);
        while(i < authTokens.size()){
            if(Objects.equals(authTokens.get(i).username(), username)){
                authToken = authTokens.get(i);
            }
            i+=1;
        }
        if( authToken.username()==null && authToken.authToken()==null) {
            throw new DataAccessException("unauthorized");
        }

        return authToken;
    }

    public void deleteAuth(String authorization)throws DataAccessException{
        boolean deleteSucees = false;
        int i = 0;
        while(i < authorizations.size()){
            if(Objects.equals(authorizations.get(i), authorization)){
                authorizations.remove(i);
                deleteSucees = true;
            }
            i+=1;

        }
        if(!deleteSucees){
            throw new DataAccessException("unauthorized");
        }
    }
    public void clear(){
        authorizations.clear();
        authTokens.clear();
    }

}
