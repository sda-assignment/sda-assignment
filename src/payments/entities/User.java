package payments.entities;

import datastore.Entity;
//import java.lang;

public class User extends Entity {
    public final String email;
    public final String username;
    public final String password;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String storify() {
        return email + ":" + username + ":" + password;
    }

    public int compareTo(Entity T)
    {
        User target = (User) T;
        if (email.equals(target.email))
        {
            System.out.println("target email " + target.email + " equals condition email " + email);

        }
        if (email.equals(target.email) && username.equals(target.username))
        {
            return 0; 
        }
        else 
        {
            int AsciiSumTarget = 0;
            int AsciiSumCurrent = 0;
 
            // loop to sum the ascii value of chars
            for (int i = 0; i < email.length(); i++) {
                AsciiSumTarget += (int)target.email.charAt(i);
                AsciiSumCurrent += (int)email.charAt(i);
            }
            
            if (AsciiSumCurrent >= AsciiSumTarget)
            {
                return 1;
            }
            else
            {
                return -1;

            }

        }

    }

}
