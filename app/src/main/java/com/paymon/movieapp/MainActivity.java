package com.paymon.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;


    public class MainActivity extends ActionBarActivity {

        public static boolean TABLET = false;
        MoviesFragment mfragment;
        public static boolean screenRotate = false;
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }

        @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState==null) {
            screenRotate = false;
        }
        setContentView(R.layout.activity_main);
        TABLET = isTablet(this);
        if (savedInstanceState == null) {
                if(!TABLET) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new MoviesFragment())
                            .commit();
                }
        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);


        //only load instance state if changing screen orientation, not in favorites since the activity doesnt take time to load favorites data
        if(!MoviesFragment.sortByFavorites && savedInstanceState!=null&&screenRotate==true)
        {



            ArrayList<String>  posters = savedInstanceState.getStringArrayList("movies");

            if(posters!=null&&this!=null) {

                ImageAdapter adapter = new ImageAdapter(this, posters, MoviesFragment.width);
                MoviesFragment.gridview.setAdapter(adapter);

            }

            screenRotate=false;


        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        screenRotate = true;
        outState.putStringArrayList("movies",  MoviesFragment.posters);



    }
    @Override
    public void onStart()
    {super.onStart();
        if(TABLET) {
            FrameLayout container1 = (FrameLayout) findViewById(R.id.container1);
            FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);



            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);


            ViewGroup.LayoutParams lp = container1.getLayoutParams();
            lp.width = size.x / 2;
            container1.setLayoutParams(lp);


            RelativeLayout.LayoutParams lpp = new RelativeLayout.LayoutParams(size.x / 2, RelativeLayout.LayoutParams.MATCH_PARENT);
            lpp.addRule(RelativeLayout.RIGHT_OF, container1.getId());
            container2.setLayoutParams(lpp);
            mfragment = new MoviesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container1, mfragment)
                    .commit();

            if(mfragment.df!=null) {

                getSupportFragmentManager().beginTransaction()
                        .remove(MoviesFragment.df)
                        .commit();
            }


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    public void trailer1(View v)
    {

        Intent intent = new Intent(this, YoutubeActivity.class)
                .putExtra("youtube",DetailFragment.youtube);

        startActivity(intent);
    }
    public void trailer2(View v)
    {
        Intent intent = new Intent(this, YoutubeActivity.class)
                .putExtra("youtube",DetailFragment.youtube2);
        startActivity(intent);
    }
    public void favorite(View v)
    {


        Button b = (Button)findViewById(R.id.favorite);
        if(b.getText().equals("FAVORITE"))
        {
            b.setText("UNFAVORITE");
            b.getBackground();

            b.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);




            ContentValues values = new ContentValues();
            values.put(MovieProvider.NAME,DetailFragment.bmp);
            values.put(MovieProvider.OVERVIEW,DetailFragment.overview);
            values.put(MovieProvider.RATING,DetailFragment.rating);
            values.put(MovieProvider.DATE,DetailFragment.date);
            values.put(MovieProvider.REVIEW,DetailFragment.review);
            values.put(MovieProvider.YOUTUBE1,DetailFragment.youtube);
            values.put(MovieProvider.YOUTUBE2,DetailFragment.youtube);
            values.put(MovieProvider.TITLE,DetailFragment.title);

            getContentResolver().insert(
                    MovieProvider.CONTENT_URI, values);


        }
        else
        {
            b.setText("FAVORITE");
            b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            getContentResolver().delete(Uri.parse("content://com.paymon.provider.Movies/movies"),
                    "title=?",new String[]{DetailFragment.title});
        }
        mfragment.onStart();
    }



}
