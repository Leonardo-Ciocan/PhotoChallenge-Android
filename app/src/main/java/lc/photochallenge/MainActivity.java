package lc.photochallenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.adapter.CategoryAdapter;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;


public class MainActivity extends ActionBarActivity {

    GridView categoryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Challenge.class);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "rfZD68sm4TmS4ji5gU84zhUPTLeLNOzZqE5UohVf", "hacFfKe0uj0waYXWXRIMBTzZ0VOFrLh5sex4ky8E");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ParseUser.logIn("leonardo", "cake");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        categoryGridView =(GridView) findViewById(R.id.gridView);

        ParseQuery<Category> CategoryParseQuery = new ParseQuery<Category>("Category");
        CategoryParseQuery.findInBackground(new FindCallback<Category>() {
            @Override
            public void done(List<Category> list, ParseException e) {
                Core.categories = (ArrayList)list;
                CategoryAdapter adapter = new CategoryAdapter(MainActivity.this , Core.categories);
                categoryGridView.setAdapter(adapter);

            }
        });

        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Core.selectedCategory = Core.categories.get(position);
                Intent i = new Intent(MainActivity.this , ChallengesActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
