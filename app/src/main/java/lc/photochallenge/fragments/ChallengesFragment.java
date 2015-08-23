package lc.photochallenge.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.ChallengeActivity;
import lc.photochallenge.ChallengesActivity;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.adapter.ChallengeAdapter;
import lc.photochallenge.models.Challenge;

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
    CircleProgress circleProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_challenges, container, false);
        ButterKnife.bind(this, v);
        adapter = new ChallengeAdapter(getActivity(), ChallengesActivity.Challenges.get(getArguments().getInt("category")));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Core.selectedChallenge = ChallengesActivity.Challenges.get(getArguments().getInt("category")).get(position);
                Intent i = new Intent(getActivity(), ChallengeActivity.class);
                startActivity(i);
            }
        });

        updateProgress();

        return v;
    }

    void updateProgress(){
        float completed = 0;
        ArrayList<Challenge> challenges = ChallengesActivity.Challenges.get(getArguments().getInt("category"));
        for(Challenge c : ChallengesActivity.Challenges.get(getArguments().getInt("category"))){
            if(c.getSubmission() != null) completed++;
        }

        int perc = Math.round(completed / ChallengesActivity.Challenges.get(getArguments().getInt("category")).size() * 100);
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
