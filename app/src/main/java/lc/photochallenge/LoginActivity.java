package lc.photochallenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;
import lc.photochallenge.models.Follow;
import lc.photochallenge.models.Submission;


public class LoginActivity extends ActionBarActivity {


    @Bind(R.id.username)
    EditText username;

    @Bind(R.id.password)
    EditText password;

    @OnClick(R.id.login)
    void login(){
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString()

                , new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(parseUser != null){
                    Intent i = new Intent(LoginActivity.this , MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Submission.class);
        ParseObject.registerSubclass(Follow.class);
        ParseObject.registerSubclass(Challenge.class);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "rfZD68sm4TmS4ji5gU84zhUPTLeLNOzZqE5UohVf", "hacFfKe0uj0waYXWXRIMBTzZ0VOFrLh5sex4ky8E");


        Core.selectedChallenge = null;
        Core.selectedCategory = null;
        Core.submissions=null;
        Core.friends = null;
        Core.categories = null;
        Core.Submissions = new HashMap<>();
        Core.Challenges = new SparseArray<>();

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        if(ParseUser.getCurrentUser() != null){
            Intent i = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(i);
            this.finishActivity(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
