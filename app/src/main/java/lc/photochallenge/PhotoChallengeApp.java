package lc.photochallenge;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;
import lc.photochallenge.models.Follow;
import lc.photochallenge.models.Submission;

public class PhotoChallengeApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Submission.class);
        ParseObject.registerSubclass(Follow.class);
        ParseObject.registerSubclass(Challenge.class);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "rfZD68sm4TmS4ji5gU84zhUPTLeLNOzZqE5UohVf", "hacFfKe0uj0waYXWXRIMBTzZ0VOFrLh5sex4ky8E");


    }
}