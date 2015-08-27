package lc.photochallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lc.photochallenge.fragments.ChallengesFragment;


public class DifficultyTabAdapter extends FragmentStatePagerAdapter {
    public static String[] difficulties = new String[]{"Easy" , "Moderate" , "Hard"};
    public DifficultyTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ChallengesFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return difficulties.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return difficulties[position];
    }
}
