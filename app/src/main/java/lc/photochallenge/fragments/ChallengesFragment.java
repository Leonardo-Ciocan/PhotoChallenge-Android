package lc.photochallenge.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.ChallengeActivity;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.adapter.ChallengeAdapter;
import lc.photochallenge.models.Challenge;
import lc.photochallenge.models.Submission;

public class ChallengesFragment extends android.support.v4.app.Fragment {

    private ChallengeAdapter adapter;

    public static ChallengesFragment newInstance(int category) {
        ChallengesFragment fragment = new ChallengesFragment();
        Bundle args = new Bundle();
        args.putInt("category" , category);
        fragment.setArguments(args);
        return fragment;
    }

    public ChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    String[] messages = new String[]{
            "Time to start",
            "Get to work",
            "Almost there",
            "Alright!"
    };

    @Bind(R.id.gridView)
    GridView gridView;

    @Bind(R.id.message)
    TextView message;

    @Bind(R.id.circle_progress)
    ProgressBar circleProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_challenges, container, false);
        ButterKnife.bind(this, v);
        adapter = new ChallengeAdapter(getActivity(), Core.Challenges.get(getArguments().getInt("category")));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Core.selectedChallenge = Core.Challenges.get(getArguments().getInt("category")).get(position);
                //Intent i = new Intent(getActivity(), ChallengeActivity.class);
                //startActivity(i);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ImageView cardImage = (ImageView) view.findViewById(R.id.img);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), cardImage, "cardImage");
                    Intent intent = new Intent(getActivity(), ChallengeActivity.class);
                    getActivity().startActivity(intent, options.toBundle());
                }
                else{
                    Intent i = new Intent(getActivity(), ChallengeActivity.class);
                    startActivity(i);
                }
            }
        });

        for(final Challenge c : Core.Challenges.get(getArguments().getInt("category"))){
            ParseQuery<Submission> submissionParseQuery = new ParseQuery<Submission>("Submission");
            submissionParseQuery.whereEqualTo("challenge" , c);
            submissionParseQuery.whereEqualTo("user", ParseUser.getCurrentUser());
            submissionParseQuery.findInBackground(new FindCallback<Submission>() {
                @Override
                public void done(List<Submission> list, ParseException e) {
                    if(list != null && list.size()>0)
                        Core.Submissions.put(c , list.get(0));
                    adapter.notifyDataSetChanged();

                    updateProgress();
                }
            });
        }


        return v;
    }

    void updateProgress(){
        float completed = 0;
        ArrayList<Challenge> challenges = Core.Challenges.get(getArguments().getInt("category"));
        for(Challenge c : Core.Challenges.get(getArguments().getInt("category"))){
            if(Core.Submissions.containsKey(c)) completed++;
        }

        int perc = Math.round(completed / Core.Challenges.get(getArguments().getInt("category")).size() * 100);
        circleProgress.setProgress(perc);


        for(int x= 0; x < messages.length;x++){
            if(perc > (messages.length - x - 1) * 25 ) {
                message.setText(messages[messages.length-1-x]);
                break;
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        updateProgress();
        adapter.notifyDataSetChanged();

    }
}
