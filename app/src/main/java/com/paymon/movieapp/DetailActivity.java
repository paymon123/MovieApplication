package com.paymon.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
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

        MainActivity.screenRotate=false;
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
    }

}