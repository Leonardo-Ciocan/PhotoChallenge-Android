package lc.photochallenge;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lc.photochallenge.R;
import lc.photochallenge.adapter.FollowingAdapter;
import lc.photochallenge.models.Follow;

public class SearchUserDialog extends Dialog {
    private final View parent;
    public Activity c;

    public FollowingAdapter adapter;
    public SearchUserDialog(Activity a , FollowingAdapter adapter , View parent) {
        super(a);
        this.c = a;
        this.parent = parent;
        this.adapter = adapter;
    }

    @Bind(R.id.name)
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ButterKnife.bind(this);
    }

    @OnClick(R.id.follow)
    void follow(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", name.getText().toString());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(final List<ParseUser> list, ParseException e) {
                if (list != null && list.size() > 0) {
                    ParseQuery<Follow> duplicateQuery = new ParseQuery<Follow>("Follow");
                    duplicateQuery.whereEqualTo("user", ParseUser.getCurrentUser());
                    duplicateQuery.whereEqualTo("following", list.get(0));
                    duplicateQuery.findInBackground(new FindCallback<Follow>() {
                                                        @Override
                                                        public void done(List<Follow> duplicates, ParseException e) {
                                                            if (duplicates == null || duplicates.size() == 0) {
                                                                Follow follow = new Follow();
                                                                follow.setFrom(ParseUser.getCurrentUser());
                                                                follow.setTo(list.get(0));
                                                                follow.saveInBackground();
                                                                adapter.add(follow);
                                                                SearchUserDialog.this.dismiss();
                                                                adapter.notifyDataSetChanged();
                                                            } else {
                                                                {
                                                                    SearchUserDialog.this.dismiss();
                                                                    Snackbar
                                                                            .make(parent, "You're already following this user", Snackbar.LENGTH_LONG)
                                                                            .show();
                                                                }
                                                            }
                                                        }
                                                    }
                    );

                } else {
                    SearchUserDialog.this.dismiss();
                    Snackbar
                            .make(parent, "There is no user with that name", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    public void clear(){
        if(name!=null)name.setText("");
    }
}
