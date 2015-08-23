package lc.photochallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lc.photochallenge.fragments.CategoriesFragment;
import lc.photochallenge.fragments.ChallengesFragment;
import lc.photochallenge.fragments.ProfileFragment;

public class MainTabAdapter  extends FragmentStatePagerAdapter {
    String[] pages = new String[]{"Categories" , "Friends" , "Profile"};
    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? CategoriesFragment.newInstance() : ProfileFragment.newInstance();
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages[position];
    }
}

