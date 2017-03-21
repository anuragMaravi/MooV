package com.anuragmaravi.moov;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
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
 * Created by anuragmaravi on 12/05/16.
 */
public class ActorProfile extends AppCompatActivity {

    TextView name_textView;
    TextView biography_textView;
    TextView date_of_birth_textView;
    TextView place_of_birth_textView;
    ImageView actor_profile_imageView;
    String final_actor_descrption;
    String actor_id;
    private RecyclerView actor_movies_recyclerView;
    private RecyclerView.LayoutManager actor_movies_layoutManager;
    private  RecyclerView.Adapter adaptera;

    private RecyclerView actor_images_recyclerView;
    private RecyclerView.LayoutManager actor_images_layoutManager;
    private  RecyclerView.Adapter actor_image_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actor_profile);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actor_id=getIntent().getStringExtra("actor_id");
        //Toast.makeText(ActorProfile.this, actor_id, Toast.LENGTH_SHORT).show();
        final_actor_descrption="http://api.themoviedb.org/3/person/"+actor_id+"?api_key=0744794205a0d39eef72cad8722d4fba";
        new ParseActorDescription().execute(final_actor_descrption);


        //credits
        actor_movies_recyclerView = (RecyclerView) findViewById(R.id.recyclerView_actor_movies);
        actor_movies_recyclerView.setHasFixedSize(true);
        actor_movies_layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        actor_movies_recyclerView.setLayoutManager(actor_movies_layoutManager);
        new ParseActorMovies().execute("http://api.themoviedb.org/3/person/"+actor_id+"/movie_credits?api_key=0744794205a0d39eef72cad8722d4fba");


        //Actor Images
        actor_images_recyclerView = (RecyclerView) findViewById(R.id.recyclerView_actor_images);
        actor_images_recyclerView.setHasFixedSize(true);
        actor_images_layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        actor_images_recyclerView.setLayoutManager(actor_images_layoutManager);
        new ParseActorImages().execute("http://api.themoviedb.org/3/person/"+actor_id+"/images?api_key=0744794205a0d39eef72cad8722d4fba");
        Log.i("Actor Id",actor_id);

        //Set Fonts
        Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");


        TextView textView3 = (TextView) findViewById(R.id.textView_biography);
        TextView textView4 = (TextView) findViewById(R.id.textViewactor_movies);
        TextView textView5 = (TextView) findViewById(R.id.textView_actor_images);


        textView3.setTypeface(typeface_light);
        textView4.setTypeface(typeface_light);
        textView5.setTypeface(typeface_light);


    }

    //Actor Description
    public class ParseActorDescription extends AsyncTask<String, String, String> {

        String name,place_of_birth,actor_id,birthday,biography,actor_profile_path,final_actor_profile_path;

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
                name=parentObject.getString("name");
                getSupportActionBar().setTitle(name);
                birthday=parentObject.getString("birthday");
                place_of_birth=parentObject.getString("place_of_birth");
                actor_id=parentObject.getString("id");
                actor_profile_path=parentObject.getString("profile_path");
                final_actor_profile_path="http://image.tmdb.org/t/p/w300"+actor_profile_path;
                biography = parentObject.getString("biography");


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
            name_textView=(TextView) findViewById(R.id.actor_name);
            place_of_birth_textView=(TextView) findViewById(R.id.actor_pob);
            date_of_birth_textView=(TextView) findViewById(R.id.actor_dob);
            biography_textView=(TextView) findViewById(R.id.actor_biography);
            actor_profile_imageView= (ImageView) findViewById(R.id.actor_profile_image);


            name_textView.setText("Name:\n "+name);
            date_of_birth_textView.setText("Date of Birth:\n "+birthday);
            place_of_birth_textView.setText("Place of Birth: \n"+place_of_birth);
            biography_textView.setText(biography);
            ImageLoader.getInstance().displayImage(final_actor_profile_path, actor_profile_imageView);

            //Set Fonts
            Typeface typeface_thin = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");
            Typeface typeface_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");

            name_textView.setTypeface(typeface_light);
            date_of_birth_textView.setTypeface(typeface_light);
            place_of_birth_textView.setTypeface(typeface_light);
            biography_textView.setTypeface(typeface_thin);

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
                JSONArray parentArray = parentObject.getJSONArray("cast");

                for(int i=0;i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ListItem movieModel = new ListItem();
                    movieModel.setCredits_character(finalObject.getString("character"));
                    movieModel.setMovie_id(finalObject.getString("id"));


                    String poster_path = finalObject.getString("poster_path");
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
            adaptera = new AdapterActorProfile((ArrayList<ListItem>) movieModelList, getApplicationContext());
            actor_movies_recyclerView.setAdapter(adaptera);
        }
    }


    //Actor Movies
    public class ParseActorImages extends AsyncTask<String, String, List<ListItem>> {
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
                JSONArray parentArray = parentObject.getJSONArray("profiles");

                for(int i=0;i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ListItem movieModel = new ListItem();

                    String poster_path = finalObject.getString("file_path");
                    String final_poster_path="http://image.tmdb.org/t/p/w300"+poster_path;
                    movieModel.setActor_image_paths(final_poster_path);
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
            actor_image_adapter = new AdapterActorImages((ArrayList<ListItem>) movieModelList, getApplicationContext());
            actor_images_recyclerView.setAdapter(actor_image_adapter);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
