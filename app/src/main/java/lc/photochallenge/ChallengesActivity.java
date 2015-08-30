package lc.photochallenge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import lc.photochallenge.models.Challenge;


public class ChallengesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        Core.Challenges.clear();

        final ViewPager pager = (ViewPager)findViewById(R.id.pager);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);


        Core.Challenges.clear();
        ParseQuery<Challenge> CategoryParseQuery = new ParseQuery<Challenge>("Challenge");
        CategoryParseQuery.whereEqualTo("category", Core.selectedCategory);
        CategoryParseQuery.orderByAscending("name");
        CategoryParseQuery.findInBackground(new FindCallback<Challenge>() {
            @Override
            public void done(List<Challenge> list, ParseException e) {
                for (Challenge c : list) {
                    if (Core.Challenges.get(c.getDifficulty()) == null) {
                        ArrayList<Challenge> cs = new ArrayList<Challenge>();
                        cs.add(c);
                        Core.Challenges.put(c.getDifficulty(), cs);
                    } else {
                        Core.Challenges.get(c.getDifficulty()).add(c);
                    }
                }

                pager.setAdapter(new DifficultyTabAdapter(getSupportFragmentManager()));
                tabs.setupWithViewPager(pager);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar_title.setText(Core.selectedCategory.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenges, menu);
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
