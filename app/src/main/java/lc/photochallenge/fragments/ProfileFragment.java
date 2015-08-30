package lc.photochallenge.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import lc.photochallenge.ChallengesActivity;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.adapter.CategoryAdapter;
import lc.photochallenge.adapter.ChallengeAdapter;
import lc.photochallenge.models.Category;

public class ProfileFragment  extends android.support.v4.app.Fragment {

    public static ProfileFragment profileFragment;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        profileFragment = fragment;
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Bind(R.id.name)
    EditText name;

    @Bind(R.id.username)
    TextView username;

    @Bind(R.id.progress)
    ArcProgress progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);

        username.setText(ParseUser.getCurrentUser().getUsername());

        name.setText(ParseUser.getCurrentUser().getString("name"));
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ParseUser.getCurrentUser().put("name", name.getText().toString());
                    ParseUser.getCurrentUser().saveInBackground();
                }
            }
        });
        updateProgress();
        return v;
    }

    public void updateProgress(){
        progress.setProgress((int)((float)Core.completedChallenges / Core.totalChallenges * 100));
    }
}
