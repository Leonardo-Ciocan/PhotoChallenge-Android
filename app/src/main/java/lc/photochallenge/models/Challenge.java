package lc.photochallenge.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Challenge")
public class Challenge extends ParseObject{
    public String getName(){
        return getString("name");
    }

    public String getDescription(){
        return getString("description");
    }

    public Category getCategory(){
        return (Category)get("category");
    }

    public int getDifficulty(){
        return getInt("difficulty");
    }
}
