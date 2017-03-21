package com.anuragmaravi.moov;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuragmaravi on 16/05/16.
 */
public class MovieSearchResults extends ActionBarActivity {
    private RecyclerView actor_movies_recyclerView;
    private RecyclerView.LayoutManager actor_movies_layoutManager;
    private  RecyclerView.Adapter adaptera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;


    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            query=query.replace(" ","+");
            actor_movies_recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            actor_movies_recyclerView.setHasFixedSize(true);
            actor_movies_layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
            actor_movies_recyclerView.setLayoutManager(actor_movies_layoutManager);
            new ParseActorMovies().execute("http://api.themoviedb.org/3/search/movie?query="+query+"&api_key=0744794205a0d39eef72cad8722d4fba");

            //Set Fonts
            Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
            Typeface typeface_thin = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");

            TextView textView1 = (TextView) findViewById(R.id.textView_search);
            textView1.setTypeface(typeface_light);
        }
    }

    //Actor Movies
    public class ParseActorMovies extends AsyncTask<String, String, List<ListItem>> {
        List<ListItem> movieModelList = new ArrayList<>();

        @Override
        protected List<ListItem> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;                                     //Declaring a http connection
            try {
                URL url = new URL(params[0]);                                //The Url that you intend to connect
                connection= (HttpURLConnection) url.openConnection(); //Initialising the connection
                connection.connect();                                 //Connecting to the Url
                InputStream stream = connection.getInputStream();     //Get the stream from the url
                reader = new BufferedReader(new InputStreamReader(stream)); //This reader will read the stream line by line
                StringBuffer buffer = new StringBuffer();             //To store the whole data
                String line = "";                                     //Temporary string variable to store the data after each line is read
                while((line= reader.readLine())!=null){
                    buffer.append(line);
                }
                String finalJson= buffer.toString();
                JSONObject parentObject= new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                for(int i=0;i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ListItem movieModel = new ListItem();
                    movieModel.setCredits_character(finalObject.getString("original_title"));
                    movieModel.setMovie_id(finalObject.getString("id"));

                    String poster_path = finalObject.getString("poster_path");
                    String final_poster_path="http://image.tmdb.org/t/p/w500"+poster_path;
                    movieModel.setCredits_profile_path(final_poster_path);
                    movieModelList.add(movieModel);
                }

                return movieModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{                                          //To close the reader and to disconnect
                if(connection!=null){
                    connection.disconnect();}
                try {
                    if(reader!=null){reader.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return  null;

        }

        @Override
        protected void onPostExecute(List<ListItem> result) {
            super.onPostExecute(result);
            adaptera = new AdapterActorProfile((ArrayList<ListItem>) movieModelList,getApplicationContext());
            actor_movies_recyclerView.setAdapter(adaptera);
        }
    }
}

