package lc.photochallenge.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.SquareView;
import lc.photochallenge.fragments.ProfileFragment;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Follow;

public class FollowingAdapter   extends ArrayAdapter<Follow> {
    public FollowingAdapter(Context context , ArrayList<Follow> books){
        super(context , R.layout.following_item , books);
    }

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.username)
    TextView username;



    @Bind(R.id.progress)
    ProgressBar progress;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
//             LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  //           convertView = inflater.inflate( R.layout.following_item , null);
            convertView = new SquareView(getContext() , R.layout.following_item);
        }

        ButterKnife.bind(this, convertView);

        ParseUser user = getItem(position).getTo();
        try {
            user.fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        name.setText(user.getString("name"));
        username.setText(user.getUsername());


        ParseQuery query2 = new ParseQuery("Submission");
        query2.whereEqualTo("user", user);
        query2.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                progress.setProgress((int)((float)i / Core.totalChallenges * 100));
            }
        });

        return convertView;
    }
}
