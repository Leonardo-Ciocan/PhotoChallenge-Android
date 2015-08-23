package lc.photochallenge.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.SquareView;
import lc.photochallenge.models.Category;
import lc.photochallenge.models.Challenge;

/**
 * Created by leo on 23/08/15.
 */
public class ChallengeAdapter extends ArrayAdapter<Challenge> {
    public ChallengeAdapter(Context context , ArrayList<Challenge> books){
        super(context , R.layout.challenge_item , books);
    }

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.img)
    FrameLayout image;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // convertView = inflater.inflate( R.layout.book_tile , null);
            convertView = new SquareView(getContext() , R.layout.challenge_item);
        }

        Challenge selectedChallenge = getItem(position);
        ButterKnife.bind(this, convertView);

        name.setText(getItem(position).getName());

        if( selectedChallenge.getSubmission() != null){
            byte[] bytes = new byte[0];
            try {
                bytes = selectedChallenge.getSubmission().getData();
                image.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}