package com.anuragmaravi.moov;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by RISHABH on 18-Apr-16.
 */
public class AdapterTopRatedMovies extends RecyclerView.Adapter<AdapterTopRatedMovies.ViewHolder> {

    ArrayList<ListItem> listItems;
    Context ctx;

    public AdapterTopRatedMovies(ArrayList<ListItem> items, Context ctx){
        super();
        this.listItems=items;
        this.ctx=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_ratings_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,ctx,listItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  listItems.get(position);
        holder.textViewTitle.setText(list.getTitle());
        holder.textViewPopularity.setText("Popularity:   "+list.getPopularity());
        holder.textViewReleaseDate.setText("Release Date:   "+list.getRelease_date());
        holder.textViewRating.setText("Rating:   "+list.getVote_average());
        holder.ratingBar.setRating(list.getVote_average()/2);
        ImageLoader.getInstance().displayImage(listItems.get(position).getPoster_path(),holder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewPopularity;
        public TextView textViewTitle;
        public TextView textViewRating;
        public TextView textViewReleaseDate;
        public ImageView imageViewPoster;
        public RatingBar ratingBar;

        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<ListItem> listItems) {
            super(itemView);
            this.listItems=listItems;
            this.ctx=ctx;
            itemView.setOnClickListener(this);

            textViewRating = (TextView) itemView.findViewById(R.id.rating_text);
            textViewTitle = (TextView) itemView.findViewById(R.id.rating_title);
            textViewReleaseDate = (TextView) itemView.findViewById(R.id.rating_release_date);
            textViewPopularity = (TextView) itemView.findViewById(R.id.rating_popularity);
            imageViewPoster=(ImageView)itemView.findViewById(R.id.rating_poster);
            ratingBar= (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            ListItem listItems=this.listItems.get(position);
            Intent intent = new Intent(this.ctx,Description.class);
            intent.putExtra("movie_id",listItems.getMovie_id());
            this.ctx.startActivity(intent);
        }
    }
}
