package com.paymon.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MoviesFragment extends Fragment {
static GridView gridview;
static int width;
static boolean sortByPop = true;
static boolean sortByFavorites = false;
static ArrayList<String> overviews;
static ArrayList<String> posters;
static ArrayList<String> titles;
static ArrayList<String> dates;
static ArrayList<String> ratings;
static ArrayList<String> youtubes;
static ArrayList<String> youtubes2;
static ArrayList<String> ids;
static ArrayList<Boolean> favorited;
static ArrayList<ArrayList<String>> comments;
static ArrayList<String> overviewsF;
static ArrayList<String> postersF;
static ArrayList<String> titlesF;
static ArrayList<String> datesF;
static ArrayList<String> ratingsF;
static ArrayList<String> youtubesF;
static ArrayList<String> youtubes2F;
static ArrayList<ArrayList<String>> commentsF;

 public static DetailFragment df;
    public static PreferenceChangeListener listener;
   public static SharedPreferences prefs;



    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ArrayList<String> array = new ArrayList<String>();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(MainActivity.TABLET)
        {
            width = size.x/6;
        }
        else
        width = size.x/3;


if(array!=null&&getActivity()!=null) {
    ImageAdapter adapter = new ImageAdapter(getActivity(), array, width);
    gridview = (GridView) rootView.findViewById(R.id.gridview);


    gridview.setColumnWidth(width);
    gridview.setAdapter(adapter);
}


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (!MainActivity.TABLET) {

                    if (!sortByFavorites) {


                        favorited = bindFavoritesToMovies();

                        Intent intent = new Intent(getActivity(), DetailActivity.class).
                                putExtra("overview", overviews.get(position)).
                                putExtra("bitmap", posters.get(position))
                                .putExtra("title", titles.get(position))
                                .putExtra("date", dates.get(position))
                                .putExtra("rating", ratings.get(position))
                                .putExtra("youtube", youtubes.get(position))
                                .putExtra("youtube2", youtubes2.get(position))
                                .putExtra("comments", comments.get(position))
                                .putExtra("favorite", favorited.get(position));

                        startActivity(intent);
                    } else {



                        Intent intent = new Intent(getActivity(), DetailActivity.class).
                                putExtra("overview", overviewsF.get(position)).
                                putExtra("bitmap", postersF.get(position))
                                .putExtra("title", titlesF.get(position))
                                .putExtra("date", datesF.get(position))
                                .putExtra("rating", ratingsF.get(position))
                                .putExtra("youtube", youtubesF.get(position))
                                .putExtra("youtube2", youtubes2F.get(position))
                                .putExtra("comments", commentsF.get(position))
                                .putExtra("favorite", favorited.get(position));

                        startActivity(intent);

                    }



                } else {

                    if (!sortByFavorites) {
                        //convert bitmap to byte array
                         df = new DetailFragment();
                         Bundle b = new Bundle();





                        favorited = bindFavoritesToMovies();

                        b.putStringArrayList("review", comments.get(position));
                        b.putString("title", titles.get(position));
                        b.putString("overview", overviews.get(position));
                        b.putString("date", dates.get(position));
                        b.putString("rating", ratings.get(position));
                        b.putString("overview", overviews.get(position));
                        b.putBoolean("favorite", favorited.get(position));
                        b.putString("bitmap", posters.get(position));
                        b.putString("youtube1", ratings.get(position));
                        b.putString("youtube2", ratings.get(position));
                        df.setArguments(b);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2, df)
                                .commit();




                    } else {

                        df = new DetailFragment();
                        Bundle b = new Bundle();



                        b.putStringArrayList("review", commentsF.get(position));
                        b.putString("title", titlesF.get(position));
                        b.putString("overview", overviewsF.get(position));
                        b.putString("date", datesF.get(position));
                        b.putString("rating", ratingsF.get(position));
                        b.putString("overview", overviewsF.get(position));
                        b.putBoolean("favorite", favorited.get(position));
                        b.putString("bitmap", postersF.get(position));
                        b.putString("youtube1", youtubesF.get(position));
                        b.putString("youtube2", youtubes2F.get(position));


                        df.setArguments(b);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container2, df)
                                .commit();





                    }

                }
            }
        });

        return rootView;
    }
    // Handle preferences changes
    private class PreferenceChangeListener implements
            SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs,
                                              String key) {

try {
    gridview.setAdapter(null);
    MainActivity.screenRotate=false;
    onStart();

}catch(Exception e){}
        }
    }

    public void onStart()
    {

        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        listener = new PreferenceChangeListener();
        prefs.registerOnSharedPreferenceChangeListener(listener);


        if(prefs.getString("sortby","popularity").equals("popularity"))
        {

            getActivity().setTitle("Most Popular Movies");
            sortByPop=true;
            sortByFavorites = false;
        }
        else if (prefs.getString("sortby","popularity").equals("rating")){
            getActivity().setTitle("Highest Rated Movies");
            sortByPop=false;
            sortByFavorites = false;
        }
        else if (prefs.getString("sortby","popularity").equals("favorites")){
            getActivity().setTitle("Favorited Movies");
            sortByPop=false;
            sortByFavorites = true;

        }
        TextView textView = new TextView(getActivity());
        LinearLayout layout = (LinearLayout)getActivity().findViewById(R.id.linearlayout);
        if(!MainActivity.screenRotate)
        loadFavoritesData();
        //if favorites is selected, does not get data from internet but loads from database instead
        if(sortByFavorites)
        {



            if(postersF.size()==0)
            {

                textView.setText("You have no favorited movies.");
                if(layout.getChildCount()==1)
                layout.addView(textView);
                gridview.setVisibility(GridView.GONE);



            }
            else{

                gridview.setVisibility(GridView.VISIBLE);
                layout.removeView(textView);

            }

                if(postersF!=null&&getActivity()!=null) {
                    ImageAdapter adapter = new ImageAdapter(getActivity(), postersF, width);
                    gridview.setAdapter(adapter);
                }

        }
        else{


        gridview.setVisibility(GridView.VISIBLE);
        layout.removeView(textView);
        if(isNetworkAvailable()) {
            if(!MainActivity.screenRotate)
            new ImageLoadTask().execute();
        }
            else{TextView textview1 = new TextView(getActivity());
            LinearLayout layout1 = (LinearLayout)getActivity().findViewById(R.id.linearlayout);
            textview1.setText("You are not connected to the Internet.");
            if(layout1.getChildCount()==1)
            layout1.addView(textview1);
            gridview.setVisibility(GridView.GONE);

        }

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public ArrayList<Boolean> bindFavoritesToMovies() {

            ArrayList<Boolean> result = new ArrayList<Boolean>();
            //populate array
            for(int i = 0; i<titles.size();i++)
            {
                result.add(false);
            }
            for (String favoritedTitles : titlesF)
            {
                for (int x = 0; x < titles.size(); x++) {
                    if (favoritedTitles.equals(titles.get(x))) {
                        result.set(x,true);

                    }

                }
            }
            return result;


    }
    public void loadFavoritesData()
    {

        String URL = "content://com.paymon.provider.Movies/movies";
        Uri movies = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(movies, null, null, null, "title");
        postersF = new ArrayList<String>();
        titlesF = new ArrayList<String>();
        datesF = new ArrayList<String>();
        ratingsF = new ArrayList<String>();
        youtubesF = new ArrayList<String>();
        youtubes2F = new ArrayList<String>();
        commentsF = new ArrayList<ArrayList<String>>();
        overviewsF = new ArrayList<String>();
        favorited = new ArrayList<Boolean>();


        while(c.moveToNext())
        {


            postersF.add(c.getString(c.getColumnIndex(MovieProvider.NAME)));




            commentsF.add(convertStringToArrayList(c.getString(c.getColumnIndex(MovieProvider.REVIEW))));
            titlesF.add(c.getString(c.getColumnIndex(MovieProvider.TITLE)));
            overviewsF.add(c.getString(c.getColumnIndex(MovieProvider.OVERVIEW)));
            youtubesF.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE1)));
            youtubes2F.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE2)));
            datesF.add(c.getString(c.getColumnIndex(MovieProvider.DATE)));
            ratingsF.add(c.getString(c.getColumnIndex(MovieProvider.RATING)));
            favorited.add(true);



        }

    }
    public ArrayList<String> convertStringToArrayList(String s)
    {
        ArrayList<String> result =  new ArrayList<String>(Arrays.asList(s.split("divider123")));

        return result;
    }
    public class ImageLoadTask extends AsyncTask<Void, Void, ArrayList<String>> {

        protected ArrayList<String> doInBackground(Void... params) {
            while (true) {
                try {

                    posters = new ArrayList(Arrays.asList(getPathsFromAPI(sortByPop)));
                    return posters;
                } catch (Exception e) {

                    continue;
                }

            }
        }


        @Override
        protected void onPostExecute(ArrayList<String> result) {


            if (result != null&&getActivity()!=null) {


                ImageAdapter adapter = new ImageAdapter(getActivity(), result, width);
                gridview.setAdapter(adapter);
            }


        }

        public String[] getPathsFromAPI(boolean popularity) {
            while (true) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String JSONResult;
                try {


                    String urlString = null;
                    if (popularity)
                        urlString = "http://api.themo" +
                                "viedb.org/3/" +
                                "discover/movie?sort_by=popularity.desc&api_key=14bb2e410981baf68982e32ec2c4735b";
                    else{
                        urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=500&api_key=14bb2e410981baf68982e32ec2c4735b";

                    }

                    URL url = new URL(urlString);


                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.

                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.

                        return null;
                    }

                    JSONResult = buffer.toString();

                    try {
                        overviews = new ArrayList<String>(Arrays.asList(getOverviewsFromJSON(JSONResult)));
                        titles = new ArrayList<String>(Arrays.asList(getTitlesFromJSON(JSONResult)));
                        ratings = new ArrayList<String>(Arrays.asList(getRatingsFromJSON(JSONResult)));
                        dates = new ArrayList<String>(Arrays.asList(getDatesFromJSON(JSONResult)));
                        ids = new ArrayList<String>(Arrays.asList(getIdsFromJSON(JSONResult)));

                        while(true) {
                            youtubes = new ArrayList<String>(Arrays.asList(getYoutubesFromIds(ids)));
                            youtubes2 = new ArrayList<String>(Arrays.asList(getYoutubes2FromIds(ids)));

                            int nullCount = 0;
                           for(int i = 0; i<youtubes.size(); i++)
                           {
                               if(youtubes.get(i)==null)
                               {
                                   nullCount++;youtubes.set(i,"no video found");
                               }

                           }
                            for(int i = 0; i<youtubes2.size(); i++)
                            {
                                if(youtubes2.get(i)==null)
                                {
                                    nullCount++;youtubes2.set(i,"no video found");
                                }

                            }
                            if(nullCount>2)continue;
                            break;
                        }

                        comments = getReviewsFromIds(ids);
                        return getPathsFromJSON(JSONResult);

                    } catch (JSONException e) {

                        return null;
                    }

                } catch (Exception e) {



                    JSONResult = null;
                    continue;
                } finally {

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {

                        }
                    }
                }
            }
        }
        public String[] getYoutubesFromIds(ArrayList<String> ids)
        {
                String[] results = new String[ids.size()];

                for (int i = 0; i < ids.size(); i++) {

                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;
                    String JSONResult;
                    try {

                        String urlString = null;

                        urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key=14bb2e410981baf68982e32ec2c4735b";

                        URL url = new URL(urlString);

                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();
                        InputStream inputStream;
                        // Read the input stream into a String
                        inputStream = urlConnection.getInputStream();
                        StringBuffer buffer = new StringBuffer();

                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }



                        JSONResult = buffer.toString();


                        try {
                            results[i] = getYoutubeFromJSON(JSONResult);


                        } catch (JSONException e) {


                            results[i] = "no video found";

                        }

                    } catch (Exception e) {



                    } finally {

                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {


                            }
                        }
                    }
                }

                return results;


        }
        public String[] getYoutubes2FromIds(ArrayList<String> ids)
        {
            String[] results = new String[ids.size()];

            for (int i = 0; i < ids.size(); i++) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String JSONResult;
                try {


                    String urlString = null;


                    urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key=14bb2e410981baf68982e32ec2c4735b";

                    URL url = new URL(urlString);


                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    InputStream inputStream;
                    // Read the input stream into a String

                    inputStream = urlConnection.getInputStream();






                    StringBuffer buffer = new StringBuffer();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }
                    JSONResult = buffer.toString();


                    try {
                        results[i] = getYoutube2FromJSON(JSONResult);


                    } catch (JSONException e) {


                        results[i] = "no video found";

                    }

                } catch (Exception e) {



                } finally {

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {


                        }
                    }
                }
            }

            return results;


        }
        public ArrayList<ArrayList<String>> getReviewsFromIds(ArrayList<String> ids)
        {
            outerloop:
            while(true) {
                ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < ids.size(); i++) {

                    HttpURLConnection urlConnection = null;
                    BufferedReader reader = null;
                    String JSONResult;
                    try {


                        String urlString = null;


                        urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/reviews?api_key=14bb2e410981baf68982e32ec2c4735b";


                        URL url = new URL(urlString);


                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();

                        // Read the input stream into a String
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        if (inputStream == null) {
                            // Nothing to do.

                            return null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }

                        if (buffer.length() == 0) {
                            // Stream was empty.  No point in parsing.

                            return null;
                        }

                        JSONResult = buffer.toString();

                        try {

                            results.add(getCommentsFromJSON(JSONResult));

                        } catch (JSONException e) {

                            return null;
                        }

                    } catch (Exception e) {
                        continue outerloop;




                    } finally {

                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (final IOException e) {

                            }
                        }
                    }
                }

                return results;
            }
        }
        public String getYoutubeFromJSON(String JSONStringParam) throws JSONException
        {

                JSONObject JSONString = new JSONObject(JSONStringParam);
                JSONArray youtubesArray = JSONString.getJSONArray("results");
                JSONObject youtube = youtubesArray.getJSONObject(0);
                String result = youtube.getString("key");


                return result;


        }
        public String getYoutube2FromJSON(String JSONStringParam) throws JSONException
        {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray youtubesArray = JSONString.getJSONArray("results");
            JSONObject youtube;
            if(youtubesArray.length()>1) {
                youtube = youtubesArray.getJSONObject(1);
            }
            else
            {
                youtube = youtubesArray.getJSONObject(0);
            }

            String result = youtube.getString("key");


            return result;
        }
        public ArrayList<String> getCommentsFromJSON(String JSONStringParam) throws JSONException
        {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray reviewsArray = JSONString.getJSONArray("results");
            ArrayList<String> results = new ArrayList<String>();
            if(reviewsArray.length()==0)
            {
                results.add("No reviews found for this movie.");
                return results;
            }
           for(int i = 0; i<reviewsArray.length();i++)
           {
               JSONObject result = reviewsArray.getJSONObject(i);
               results.add(result.getString("content"));
           }
            return results;
        }
        public String[] getPathsFromJSON(String JSONStringParam) throws JSONException
        {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[] result = new String[moviesArray.length()];



            for(int i = 0; i < moviesArray.length(); i++)
            {
                JSONObject movie = moviesArray.getJSONObject(i);
                String moviePath = movie.getString("poster_path");
                result[i] = moviePath;



            }
            return result;

        }
        public String[] getOverviewsFromJSON(String JSONStringParam) throws JSONException
        {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[] result = new String[moviesArray.length()];


            for(int i = 0; i < moviesArray.length(); i++)
            {
                JSONObject movie = moviesArray.getJSONObject(i);
                String overview = movie.getString("overview");
                result[i] = overview;

            }
            return result;

        }
    }
    public String[] getTitlesFromJSON(String JSONStringParam) throws JSONException
        {

            JSONObject JSONString = new JSONObject(JSONStringParam);
            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[] result = new String[moviesArray.length()];


            for(int i = 0; i < moviesArray.length(); i++)
            {
                JSONObject movie = moviesArray.getJSONObject(i);
                String title= movie.getString("original_title");
                result[i] = title;


            }
        return result;

    }
    public String[] getDatesFromJSON(String JSONStringParam) throws JSONException {

        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray moviesArray = JSONString.getJSONArray("results");
        String[] result = new String[moviesArray.length()];


        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            String dates = movie.getString("release_date");
            result[i] = dates;


        }
        return result;
    }
    public String[] getIdsFromJSON(String JSONStringParam) throws JSONException
    {

        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray moviesArray = JSONString.getJSONArray("results");
        String[] result = new String[moviesArray.length()];


        for(int i = 0; i < moviesArray.length(); i++)
        {
            JSONObject movie = moviesArray.getJSONObject(i);
            String id= movie.getString("id");
            result[i] = id;


        }
        return result;

    }
    public String[] getRatingsFromJSON(String JSONStringParam) throws JSONException {

        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray moviesArray = JSONString.getJSONArray("results");
        String[] result = new String[moviesArray.length()];


        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Double number = movie.getDouble("vote_average");
            String ratings = Double.toString(number) + "/10";
            result[i] = ratings;


        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id==R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }




}
