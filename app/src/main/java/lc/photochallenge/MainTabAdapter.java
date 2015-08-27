package lc.photochallenge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import lc.photochallenge.fragments.CategoriesFragment;
import lc.photochallenge.fragments.ChallengesFragment;
import lc.photochallenge.fragments.FriendsFragment;
import lc.photochallenge.fragments.ProfileFragment;

public class MainTabAdapter  extends FragmentStatePagerAdapter {
    String[] pages = new String[]{"Categories" , "Friends" , "Profile"};
    int[] imgs = new int[]{
            R.drawable.ic_category,
            R.drawable.ic_action_action_account_child,
            R.drawable.ic_profile
    };

    Context context;
    public MainTabAdapter(FragmentManager fm , Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return CategoriesFragment.newInstance();
            case 1:
                return FriendsFragment.newInstance();
            case 2:
                return ProfileFragment.newInstance();
        }
        return CategoriesFragment.newInstance();
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return pages[position];
        Drawable image =  context.getResources().getDrawable(imgs[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}

