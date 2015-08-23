package lc.photochallenge;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lc.photochallenge.adapter.CategoryAdapter;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;


public class ChallengesActivity extends ActionBarActivity {

    public static SparseArray<ArrayList<Challenge>> Challenges = new SparseArray<ArrayList<Challenge>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        Challenges.clear();

        final ViewPager pager = (ViewPager)findViewById(R.id.pager);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);




        ParseQuery<Challenge> CategoryParseQuery = new ParseQuery<Challenge>("Challenge");
        CategoryParseQuery.whereEqualTo("category" , Core.selectedCategory);
        CategoryParseQuery.orderByAscending("name");
        CategoryParseQuery.findInBackground(new FindCallback<Challenge>() {
            @Override
            public void done(List<Challenge> list, ParseException e) {
                for(Challenge c : list){
                    if(Challenges.get(c.getDifficulty()) == null){
                        ArrayList<Challenge> cs = new ArrayList<Challenge>();
                        cs.add(c);
                        Challenges.put(c.getDifficulty() , cs);
                    }
                    else{
                        Challenges.get(c.getDifficulty()).add(c);
                    }
                }

                pager.setAdapter(new DifficultyTabAdapter(getSupportFragmentManager()));
                tabs.setupWithViewPager(pager);
            }
        });
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
