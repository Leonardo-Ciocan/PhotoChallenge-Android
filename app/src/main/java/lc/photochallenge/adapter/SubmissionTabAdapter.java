package lc.photochallenge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import lc.photochallenge.fragments.ChallengesFragment;
import lc.photochallenge.fragments.SubmissionFragment;
import lc.photochallenge.models.Submission;

public class SubmissionTabAdapter extends FragmentStatePagerAdapter {
    public List<ParseObject> submissions;
    public SubmissionTabAdapter(FragmentManager fm , List<ParseObject> submissions) {
        super(fm);
        this.submissions = submissions;
    }

    @Override
    public Fragment getItem(int position) {
        return SubmissionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return submissions.size();
    }
}

