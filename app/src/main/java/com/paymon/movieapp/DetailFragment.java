package com.paymon.movieapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    public static String youtube;
    public  static String youtube2;
    public  static String overview;
    public  static String rating;
    public  static String date;
    public  static String review;
    public  static String title;
    public  static String bmp;
    public boolean favorite;
    public static ArrayList<String> comments;
    public static Button b;
    private ShareActionProvider mShareActionProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();


        review = null;
        if (intent != null && intent.hasExtra("overview")) {

            overview = intent.getStringExtra("overview");
            TextView textview = (TextView) rootView.findViewById(R.id.overview);
            textview.setText(overview);
        }
        if (intent != null && intent.hasExtra("title")) {

            title = intent.getStringExtra("title");
            TextView textview = (TextView) rootView.findViewById(R.id.title);
            textview.setText(title);
        }
        if (intent != null && intent.hasExtra("rating")) {

            rating = intent.getStringExtra("rating");
            TextView textview = (TextView) rootView.findViewById(R.id.rating);
            textview.setText(rating);
        }
        if (intent != null && intent.hasExtra("date")) {

            date = intent.getStringExtra("date");
            TextView textview = (TextView) rootView.findViewById(R.id.date);
            textview.setText(date);
        }
        if (intent != null && intent.hasExtra("bitmap")) {

            bmp = intent.getStringExtra("bitmap");
            ImageView imageview = (ImageView) rootView.findViewById(R.id.poster);

            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+bmp).resize(MoviesFragment.width,(int)(MoviesFragment.width*1.5)).into(imageview);
        }
        if (intent != null && intent.hasExtra("youtube")) {

            youtube = intent.getStringExtra("youtube");

        }
        if (intent != null && intent.hasExtra("youtube2")) {

            youtube2 = intent.getStringExtra("youtube2");
        }
        if(intent!=null && intent.hasExtra("comments")) {
             comments = intent.getStringArrayListExtra("comments");
            for (int i = 0; i < comments.size(); i++) {

                LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
                View divider = new View(getActivity());
                TextView textview = new TextView(getActivity());
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setLayoutParams(p);
                int paddingPixel = 10;
                float density = getActivity().getResources().getDisplayMetrics().density;
                int paddingDp = (int) (paddingPixel * density);
                textview.setPadding(0, paddingDp, 0, paddingDp);
                RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                x.height = 1;
                divider.setLayoutParams(x);
                divider.setBackgroundColor(Color.BLACK);
                textview.setText(comments.get(i));
                layout.addView(divider);
                layout.addView(textview);
                if (review == null) {
                    review = comments.get(i);
                } else {
                    review += "divider123" + comments.get(i);
                }

            }
        }
            b = (Button)rootView.findViewById(R.id.favorite);
            if(intent!=null && intent.hasExtra("favorite"))
            {
                favorite = intent.getBooleanExtra("favorite",false);
                if(!favorite)
                {
                    b.setText("FAVORITE");
                    b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

                }
                else
                {
                    b.setText("UNFAVORITE");
                    b.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
                }
            }
        if (getArguments()!=null)
        {

            Bundle bundle = getArguments();
            title=bundle.getString("title");
            youtube = bundle.getString("youtube1");
            youtube2 = bundle.getString("youtube2");
            overview = bundle.getString("overview");
            date = bundle.getString("date");
            comments = bundle.getStringArrayList("review");
            bmp = bundle.getString("bitmap");

            favorite = bundle.getBoolean("favorite");

            rating = bundle.getString("rating");

            TextView textviewz = (TextView) rootView.findViewById(R.id.overview);
            textviewz.setText(overview);
            ImageView imageview = (ImageView) rootView.findViewById(R.id.poster);

            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+bmp).resize(MoviesFragment.width,(int)(MoviesFragment.width*1.5)).into(imageview);
            TextView textviewd = (TextView) rootView.findViewById(R.id.date);
            textviewd.setText(date);
            TextView textviewt = (TextView) rootView.findViewById(R.id.title);
            textviewt.setText(title);
            TextView textviewr = (TextView) rootView.findViewById(R.id.rating);
            textviewr.setText(rating);
            for (int i = 0; i < comments.size(); i++) {

                LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
                View divider = new View(getActivity());
                TextView textview = new TextView(getActivity());
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setLayoutParams(p);
                int paddingPixel = 10;
                float density = getActivity().getResources().getDisplayMetrics().density;
                int paddingDp = (int) (paddingPixel * density);
                textview.setPadding(0, paddingDp, 0, paddingDp);
                RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                x.height = 1;
                divider.setLayoutParams(x);
                divider.setBackgroundColor(Color.BLACK);
                textview.setText(comments.get(i));
                layout.addView(divider);
                layout.addView(textview);
                if (review == null) {
                    review = comments.get(i);
                } else {
                    review += "divider123" + comments.get(i);
                }

            }
            if(!favorite)
            {
                b.setText("FAVORITE");
                b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

            }
            else
            {
                b.setText("UNFAVORITE");
                b.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
            }


        }


        return rootView;
    }
    public DetailFragment() {

        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate menu resource file.

        inflater.inflate(R.menu.activity_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareActionProvider != null ) {
            mShareActionProvider.setShareIntent(createShareIntent());

        }
    }
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Check out this trailer for " + title+
                ": https://www.youtube.com/watch?v=" + youtube);

        return shareIntent;
    }




}