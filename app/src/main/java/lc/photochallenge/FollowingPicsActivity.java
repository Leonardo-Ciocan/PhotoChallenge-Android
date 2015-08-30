package lc.photochallenge;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.adapter.SubmissionTabAdapter;
import lc.photochallenge.models.Follow;
import lc.photochallenge.models.Submission;
import me.relex.circleindicator.CircleIndicator;


public class FollowingPicsActivity extends ActionBarActivity {


    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_pics);

        ButterKnife.bind(this);

        ArrayList<ParseQuery<ParseObject>> queryList = new ArrayList<ParseQuery<ParseObject>>();
        for(Follow f : Core.friends){
            ParseQuery<ParseObject> submissionParseQuery = new ParseQuery<ParseObject>("Submission");
            submissionParseQuery.whereEqualTo("challenge" , Core.selectedChallenge);
            submissionParseQuery.whereEqualTo("user" , f.getTo());
            queryList.add(submissionParseQuery);
        }

        final CircleIndicator customIndicator = (CircleIndicator) findViewById(R.id.indicator);
        ParseQuery<ParseObject> submissionParseQuery = ParseQuery.or(queryList);
        submissionParseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                Core.submissions = new ArrayList<ParseObject>(list);
                SubmissionTabAdapter adapter = new SubmissionTabAdapter(getSupportFragmentManager(), list);
                if(Core.submissions.size()==0) {
                    finish();
                    return;
                }
                pager.setAdapter(adapter);
                customIndicator.setViewPager(pager);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_following_pics, menu);
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
