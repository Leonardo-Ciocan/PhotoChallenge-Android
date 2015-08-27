package lc.photochallenge.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Follow")
public class Follow extends ParseObject{
    public ParseUser getFrom(){
        return getParseUser("user");
    }

    public void setFrom(ParseUser from){
        put("user" , from);
    }

    public ParseUser getTo(){
        return getParseUser("following");
    }

    public void setTo(ParseUser from){
        put("following" , from);
    }
}
