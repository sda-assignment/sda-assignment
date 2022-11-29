
package payments.controllers;

import java.util.ArrayList;

import payments.common.ResponseType;
import datastore.exceptions.EntitySaveException;
import payments.entities.User;
import datastore.Relation;

public class AuthController{
    private Relation<User> relation;

    public AuthController(Relation<User> relation){
        this.relation = relation;
    }

    public ResponseType signUp(String email, String userName, String password) throws EntitySaveException{
    
        ArrayList<User> temp = relation.select(u ->u.email.equals(email));

        try{ 
            if(temp.size() == 0){
                relation.insert(new User(email, userName, password, false, 0));
                return new ResponseType(true, "Signed up successfully");}
            else {
                return new ResponseType(false, "User already exists");
            }
        }
        catch (Exception e) {
            throw new EntitySaveException("Failed to sign up : " + e.toString());
        }
    }
    
    
    public ResponseType logIn (String email, String password)
    {
    
    ArrayList<User> temp = relation.select(u ->u.email.equals(email));
    
        if (temp.size() == 1)
        {
            if(temp.get(0).password.equals(password))
            {
            
                return new ResponseType(true, "Logged in successfully");
            }
            else 
            {
                return new ResponseType(false, "Password is incorrect");
            }

        }
        else
        {
            return new ResponseType(false, "user doesn't exist");
        }
    }

    public ResponseType logOut(){

        //extra logic for login session 
        return new ResponseType(false, null);
    }
}