package lc.photochallenge.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lc.photochallenge.Core;
import lc.photochallenge.R;
import lc.photochallenge.SquareView;
import lc.photochallenge.models.Challenge;
import lc.photochallenge.models.Submission;

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
    ImageView image;

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

        if(Core.Submissions.containsKey(getItem(position))){
            Submission s = Core.Submissions.get(getItem(position));
            if(s.getPhoto().getUrl().equals("")){
                /*Core.Submissions.get(getItem(position)).getPhoto().getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        image.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                        Toast.makeText(getContext() , "done",Toast.LENGTH_LONG).show();
                    }
                });*/
            }
            else{
                /*byte[] bytes;
                try {
                    bytes =  Core.Submissions.get(getItem(position)).getPhoto().getData();
                    image.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                Log.e("xaaa",s.getPhoto().getUrl());
                Picasso.with(getContext()).load(s.getPhoto().getUrl()).resize(300,300).into(image);
            }

        }


        return convertView;
    }
}