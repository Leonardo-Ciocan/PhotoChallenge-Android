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
import android.widget.GridView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.ChallengeActivity;
import lc.photochallenge.ChallengesActivity;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.adapter.ChallengeAdapter;

public class ChallengesFragment extends android.support.v4.app.Fragment {

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

    @Bind(R.id.gridView)
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_challenges, container, false);
        ButterKnife.bind(this,v);
        gridView.setAdapter(new ChallengeAdapter(getActivity(), ChallengesActivity.Challenges.get(getArguments().getInt("category"))));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Core.selectedChallenge = ChallengesActivity.Challenges.get(getArguments().getInt("category")).get(position);
                Intent i = new Intent(getActivity() , ChallengeActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

}
