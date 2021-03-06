package lc.photochallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.r0adkll.slidr.Slidr;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lc.photochallenge.models.Submission;


public class ChallengeActivity extends ActionBarActivity {

    @Bind(R.id.camera)
    FloatingActionButton camera;

    @Bind(R.id.squareView)
    ImageView image;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.category)
    TextView category;

    @Bind(R.id.followingSubs)
    TextView followingSubs;

    @Bind(R.id.following_pics)
    LinearLayout following_pics;

    Bitmap bitmap;
    private Animation introAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) postponeEnterTransition();

        introAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                camera.setRotation(interpolatedTime * 360);
                camera.setTranslationX(500 - interpolatedTime * 500);
                name.setTranslationX(1000-interpolatedTime*1000);
                category.setTranslationX(-1000+interpolatedTime*1000);
            }
        };


        Slidr.attach(this);
        ButterKnife.bind(this);


        introAnimation.setDuration(1500);
        introAnimation.setStartTime(3000);
        introAnimation.setInterpolator(new OvershootInterpolator());

        //name.setTranslationX(1000);
        //category.setTranslationX(-1000);
        //difficulty.setTranslationX(1000);
        //camera.setTranslationX(500);
        name.setText(Core.selectedChallenge.getName());

        if(Core.Submissions.containsKey(Core.selectedChallenge)){
            Submission s = Core.Submissions.get(Core.selectedChallenge);
            if(s.getPhoto().getUrl().equals("")) {
                s.getPhoto().getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        scheduleStartPostponedTransition(image);
                    }
                });
            }
            else {
                Picasso.with(this).load(s.getPhoto().getUrl()).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        scheduleStartPostponedTransition(image);
                    }

                    @Override
                    public void onError() {
                        scheduleStartPostponedTransition(image);
                    }
                });

            }
        }
        else{
            scheduleStartPostponedTransition(image);
        }

        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("challenge", Core.selectedChallenge.getObjectId());
        params.put("user", ParseUser.getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("followingSubmissions", params, new FunctionCallback<Integer>() {

            @Override
            public void done(Integer integer, ParseException e) {
                    if (e == null) {
                        if(integer == 0) return;
                        following_pics.setVisibility(View.VISIBLE);
                        followingSubs.setText(integer + " friends have submissions");
                    }
                else{
                        e.printStackTrace();
                    }
            }
        });
        category.setText(Core.selectedCategory.getName());
    }

    @OnClick(R.id.following_pics)
    void pics(){
        Intent i = new Intent( this , FollowingPicsActivity.class );
        startActivity(i);
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
                if(bitmap.getWidth() > 800)bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png" , byteArray);
                file.saveInBackground();

                if(Core.Submissions.containsKey(Core.selectedChallenge)){
                    Core.Submissions.get(Core.selectedChallenge).setPhoto(file);
                    Core.Submissions.get(Core.selectedChallenge).saveInBackground();
                }
                else{
                    Submission submission = new Submission();
                    submission.setFrom(ParseUser.getCurrentUser());
                    submission.setChallenge(Core.selectedChallenge);
                    submission.setPhoto(file);
                    submission.saveInBackground();

                    Core.Submissions.put(Core.selectedChallenge, submission);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Toast.makeText(this , "A" , Toast.LENGTH_LONG).show();

    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        Toast.makeText(this , "B" , Toast.LENGTH_LONG).show();

    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElement.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                            startPostponedEnterTransition();
                            camera.startAnimation(introAnimation);
                            return true;
                        }
                    });
        }
        else{
            camera.startAnimation(introAnimation);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        introAnimation.setInterpolator(new ReverseInterpolator());
        camera.startAnimation(introAnimation);
    }

    public class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float paramFloat) {
            return Math.abs(paramFloat -1f);
        }
    }
}
