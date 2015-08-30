package lc.photochallenge.fragments;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import lc.photochallenge.ChallengesActivity;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.SquareView;
import lc.photochallenge.models.Submission;


public class SubmissionFragment extends android.support.v4.app.Fragment {

    public static SubmissionFragment newInstance(int position) {
        SubmissionFragment fragment = new SubmissionFragment();
        Bundle args = new Bundle();
        args.putInt("position" , position);
        fragment.setArguments(args);
        return fragment;
    }

    public SubmissionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_submission, container, false);
        Submission submission = (Submission)Core.submissions.get(getArguments().getInt("position"));

        final ImageView image = (ImageView)v.findViewById(R.id.squareView);
        final TextView name = (TextView)v.findViewById(R.id.name);
        name.setText(submission.getChallenge().getName());
        final TextView author = (TextView)v.findViewById(R.id.author);
        author.setText(submission.getFrom().getString("name"));

        if(submission.getPhoto().getUrl().equals("")) {
            submission.getPhoto().getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
            });
        }
        else{
            Picasso.with(getActivity()).load(submission.getPhoto().getUrl()).into(image);
        }


        return v;
    }


}
