package lc.photochallenge.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.SearchUserDialog;
import lc.photochallenge.adapter.FollowingAdapter;
import lc.photochallenge.models.Follow;

public class FriendsFragment   extends android.support.v4.app.Fragment {


    private SearchUserDialog dialog;
    private FollowingAdapter adapter;

    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Bind(R.id.following)
    GridView followingList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, v);

        ParseQuery<Follow> followParseQuery = new ParseQuery<Follow>("Follow");
        followParseQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        followParseQuery.findInBackground(new FindCallback<Follow>() {
            @Override
            public void done(List<Follow> list, ParseException e) {
                Core.friends = list;
                adapter = new FollowingAdapter(getActivity(), (ArrayList) list);
                followingList.setAdapter(adapter);
            }
        });
        return v;
    }



    @OnClick(R.id.search)
    public void showDialog(){
        if(dialog == null){
            dialog = new SearchUserDialog(getActivity(),adapter);
        }
        dialog.clear();
        dialog.show();
    }
}