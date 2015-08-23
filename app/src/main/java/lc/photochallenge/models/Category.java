package lc.photochallenge.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Category")
public class Category extends ParseObject{
    public String getName(){
        return getString("Name");
    }
}
