package com.anuragmaravi.moov;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by anuragmaravi on 14/04/16.
 */
public class FragmentTopRatedMovies extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        new JSONTask().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=0744794205a0d39eef72cad8722d4fba");
        return rootview;

    }

    public class JSONTask extends AsyncTask<String, String, List<ListItem>> {
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
                    movieModel.setMovie_id(finalObject.getString("id"));
                    movieModel.setRelease_date(finalObject.getString("release_date"));
                    movieModel.setPopularity(finalObject.getString("popularity"));
                    movieModel.setVote_average((float) finalObject.getDouble("vote_average"));

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
            adapter = new AdapterTopRatedMovies((ArrayList<ListItem>) movieModelList,getContext());
            recyclerView.setAdapter(adapter);
        }
    }


}