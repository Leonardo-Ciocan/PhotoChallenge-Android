package lc.photochallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lc.photochallenge.models.Submission;


public class ChallengeActivity extends ActionBarActivity {

    @Bind(R.id.camera)
    FloatingActionButton camera;

    @Bind(R.id.squareView)
    SquareView image;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.category)
    TextView category;

    @Bind(R.id.difficulty)
    TextView difficulty;



    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        ButterKnife.bind(this);
        name.setText(Core.selectedChallenge.getName());

        if( ChallengesActivity.Submissions.containsKey(Core.selectedChallenge)){
            ChallengesActivity.Submissions.get(Core.selectedChallenge).getPhoto().getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    image.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                }
            });
        }

        category.setText(Core.selectedCategory.getName());
        difficulty.setText(DifficultyTabAdapter.difficulties[Core.selectedChallenge.getDifficulty()]);
    }

    @OnClick(R.id.camera)
    void chooseImage(){
        Crop.pickImage(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Crop.getOutput(result));
                image.setBackground(new BitmapDrawable(bitmap));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png" , byteArray);
                file.saveInBackground();

                if(ChallengesActivity.Submissions.containsKey(Core.selectedChallenge)){
                    ChallengesActivity.Submissions.get(Core.selectedChallenge).setPhoto(file);
                    ChallengesActivity.Submissions.get(Core.selectedChallenge).saveInBackground();
                }
                else{
                    Submission submission = new Submission();
                    submission.setFrom(ParseUser.getCurrentUser());
                    submission.setChallenge(Core.selectedChallenge);
                    submission.setPhoto(file);
                    submission.saveInBackground();

                    ChallengesActivity.Submissions.put(Core.selectedChallenge, submission);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
