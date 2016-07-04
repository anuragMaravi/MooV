package com.anuragmaravi.moov;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

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
 * Created by anuragmaravi on 12/04/16.
 */
public class Description extends Activity {

    TextView title_textView;
    TextView overview_textView;
    TextView release_date_textView;
    TextView tagline_textView;
    TextView homepage_textView;
    TextView runtime_textView;
    TextView status_textView;
    TextView vote_average_textView;
    ImageView poster_imageView;
    ImageView backdrop_imageView;
    String movie_id;
    String final_movie_id;

    //Credits fields declaration
    String credits_data_url;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  RecyclerView.Adapter adapter;

    private RecyclerView similar_movies_recyclerView;
    private RecyclerView.LayoutManager similar_layoutManager;
    private RecyclerView.Adapter similar_movies_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_description);
        movie_id=getIntent().getStringExtra("movie_id");
        //Toast.makeText(Description.this, movie_id, Toast.LENGTH_SHORT).show();
        final_movie_id="http://api.themoviedb.org/3/movie/"+movie_id+"?api_key=0744794205a0d39eef72cad8722d4fba";
        new JSONTask().execute(final_movie_id);


        //credits
        recyclerView = (RecyclerView) findViewById(R.id.cast_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        new CreditsParsing().execute("http://api.themoviedb.org/3/movie/"+movie_id+"/credits?api_key=0744794205a0d39eef72cad8722d4fba");

        //similar Movies
        similar_movies_recyclerView = (RecyclerView) findViewById(R.id.similar_movies_recyclerView);
        similar_movies_recyclerView.setHasFixedSize(true);
        similar_layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similar_movies_recyclerView.setLayoutManager(similar_layoutManager);
        new SimilarMoviesParsing().execute("http://api.themoviedb.org/3/movie/"+movie_id+"/similar?api_key=0744794205a0d39eef72cad8722d4fba");


        //Font
        Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
        Typeface typeface_thin = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");


        TextView textView = (TextView) findViewById(R.id.text);
        TextView textView1 = (TextView) findViewById(R.id.textView_cast);
        TextView textView2 = (TextView) findViewById(R.id.textView_homepage);
        TextView textView3 = (TextView) findViewById(R.id.textView_similarMovies);

        textView.setTypeface(typeface_light);
        textView1.setTypeface(typeface_light);
        textView2.setTypeface(typeface_light);
        textView3.setTypeface(typeface_light);



    }

    //Credits
    public class CreditsParsing extends AsyncTask<String, String, List<ListItem>> {
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
                JSONArray parentArray = parentObject.getJSONArray("cast");

                for(int i=0;i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ListItem movieModel = new ListItem();
                    movieModel.setCredits_name(finalObject.getString("name"));
                    movieModel.setCredits_character(finalObject.getString("character"));
                    movieModel.setActor_id(finalObject.getString("id"));

                    String poster_path = finalObject.getString("profile_path");
                    String final_poster_path="http://image.tmdb.org/t/p/w300"+poster_path;
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
            adapter = new AdapterCredits((ArrayList<ListItem>) movieModelList,getBaseContext());
            recyclerView.setAdapter(adapter);
        }
    }

    //Description
    public class JSONTask extends AsyncTask<String, String, String> {

        String title,overview,release_date,tagline,homepage,poster_path,backdrop_path,final_backdrop_path,final_poster_path,runtime,status,vote_average;

        @Override
        protected String doInBackground(String... params) {
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
                    title=parentObject.getString("original_title");
                    release_date="Release Date: \n"+parentObject.getString("release_date");
                    tagline=parentObject.getString("tagline");
                    homepage=parentObject.getString("homepage");
                    poster_path=parentObject.getString("poster_path");
                    runtime="Runtime:  "+parentObject.getString("runtime");
                    status="Status:  \n"+parentObject.getString("status");
                    vote_average="Rating:  "+parentObject.getString("vote_average");
                    final_poster_path="http://image.tmdb.org/t/p/w300"+poster_path;
                    overview = parentObject.getString("overview");
                    backdrop_path=parentObject.getString("backdrop_path");
                    final_backdrop_path="http://image.tmdb.org/t/p/w1000/"+backdrop_path;


                return null;
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            title_textView=(TextView) findViewById(R.id.description_title);
            overview_textView=(TextView) findViewById(R.id.description_overview);
            release_date_textView=(TextView) findViewById(R.id.description_release_date);
            homepage_textView=(TextView) findViewById(R.id.description_homepage);
            tagline_textView=(TextView) findViewById(R.id.description_tagline);
            runtime_textView=(TextView) findViewById(R.id.description_runtime);
            status_textView=(TextView) findViewById(R.id.description_status);
            vote_average_textView=(TextView) findViewById(R.id.description_rating);
            poster_imageView=(ImageView) findViewById(R.id.description_poster);
            backdrop_imageView=(ImageView) findViewById(R.id.description_backdrop);


            title_textView.setText(title);
            overview_textView.setText(overview);
            release_date_textView.setText(release_date);
            homepage_textView.setText(homepage);
            tagline_textView.setText("\""+tagline+"\"");
            runtime_textView.setText(runtime);
            status_textView.setText(status);
            vote_average_textView.setText(vote_average);
            ImageLoader.getInstance().displayImage(final_poster_path, poster_imageView);
            ImageLoader.getInstance().displayImage(final_backdrop_path, backdrop_imageView);

            //set Font
            Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
            Typeface typeface_thin = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");

            title_textView.setTypeface(typeface_light);
            tagline_textView.setTypeface(typeface_light);
            overview_textView.setTypeface(typeface_thin);
            release_date_textView.setTypeface(typeface_thin);
            homepage_textView.setTypeface(typeface_thin);
            runtime_textView.setTypeface(typeface_thin);
            status_textView.setTypeface(typeface_thin);
            vote_average_textView.setTypeface(typeface_thin);

        }
    }

    //Similar Movies
    public class SimilarMoviesParsing extends AsyncTask<String, String, List<ListItem>> {
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
                    movieModel.setTitle(finalObject.getString("original_title"));
                    movieModel.setUpcoming_movie_id(finalObject.getString("id"));

                    String poster_path = finalObject.getString("poster_path");
                    String final_poster_path="http://image.tmdb.org/t/p/w300"+poster_path;
                    movieModel.setPoster_path(final_poster_path);
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
            similar_movies_adapter = new AdapterSimilarMovies((ArrayList<ListItem>) movieModelList, Description.this);
            similar_movies_recyclerView.setAdapter(similar_movies_adapter);
        }
    }


}
