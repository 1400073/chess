package service;

import dataAccess.*;

import model.AuthData;
import model.UserData;

import java.util.Objects;

public class UserService {
   public AuthData register(UserData user) throws DataAccessException {
      User userInterface = new User();
      UserData checkUser = userInterface.getUser(user.username());
      Auth authInterface = new Auth();
      if(checkUser.username() == null){
         userInterface.createUser(user.username(), user.password(), user.email());

      }
      else{
         throw new DataAccessException("already taken");
      }
      return authInterface.createAuth(user.username());
   }
   public AuthData login(UserData user) throws DataAccessException {
      User userInterface = new User();
      UserData checkUser = userInterface.getUser(user.username());

      if(!Objects.equals(user.password(), checkUser.password()) || checkUser.username() == null){
         throw new DataAccessException("unauthorized");
      }
      Auth authInterface = new Auth();
      return authInterface.createAuth(user.username());

   }

   public void logout(UserData user) throws DataAccessException {
      Auth auth = new Auth();
      AuthData tempAuth = auth.getAuthToken(user.username());
      String tempAuthorization = auth.getAuth(tempAuth.authToken());
      auth.deleteAuth(tempAuthorization);

   }

}
