package lc.photochallenge;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
        final ViewPager pager = (ViewPager)findViewById(R.id.pager);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        try {
            ParseUser.logIn("leonardo", "cake");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pager.setAdapter(new MainTabAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);

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
