package lc.photochallenge.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Submission")
public class Submission extends ParseObject{
    public ParseUser getFrom(){
        return getParseUser("user");
    }

    public void setFrom(ParseUser from){
        put("user" , from);
    }

    public Challenge getChallenge(){
        return (Challenge)get("challenge");
    }

    public void setChallenge(Challenge challenge){
        put("challenge" , challenge);
    }

    public ParseFile getPhoto(){
        return getParseFile("photo");
    }

    public void setPhoto(ParseFile file){
        put("photo" , file);
    }
}
