package lc.photochallenge.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.ChallengeActivity;
import lc.photochallenge.ChallengesActivity;
import lc.photochallenge.Core;
import lc.photochallenge.MainActivity;
import lc.photochallenge.R;
import lc.photochallenge.adapter.CategoryAdapter;
import lc.photochallenge.adapter.ChallengeAdapter;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;

public class CategoriesFragment extends android.support.v4.app.Fragment {

    private ChallengeAdapter adapter;
    private GridView categoryGridView;

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, v);

        categoryGridView =(GridView) v.findViewById(R.id.gridView);

        ParseQuery<Category> CategoryParseQuery = new ParseQuery<Category>("Category");
        CategoryParseQuery.findInBackground(new FindCallback<Category>() {
            @Override
            public void done(List<Category> list, ParseException e) {
                Core.categories = (ArrayList)list;
                CategoryAdapter adapter = new CategoryAdapter(getActivity() , Core.categories);
                categoryGridView.setAdapter(adapter);

            }
        });

        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Core.selectedCategory = Core.categories.get(position);
                Intent i = new Intent(getActivity(), ChallengesActivity.class);
                startActivity(i);
            }
        });
        return v;
    }


}
