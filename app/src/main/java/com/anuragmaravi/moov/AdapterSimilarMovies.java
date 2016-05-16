package com.anuragmaravi.moov;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by anuragmaravi on 20/04/16.
 */
public class AdapterSimilarMovies extends RecyclerView.Adapter<AdapterSimilarMovies.ViewHolder> {

    ArrayList<ListItem> listItems;
    Context ctx;


    public AdapterSimilarMovies(ArrayList<ListItem> items,Context ctx){
        super();
        this.listItems=items;
        this.ctx=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.similar_movies, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,ctx,listItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(listItems.get(position).getPoster_path(),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        ArrayList<ListItem> listItems = new ArrayList<>();
        Context ctx;


        public ViewHolder(View itemView, Context ctx, ArrayList<ListItem> listItems) {
            super(itemView);
            this.listItems=listItems;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            imageView=(ImageView) itemView.findViewById(R.id.similar_movies_poster);

        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            ListItem listItems=this.listItems.get(position);
            Intent intent = new Intent(this.ctx,Description.class);
            intent.putExtra("movie_id",listItems.getUpcoming_movie_id());
            this.ctx.startActivity(intent);
        }

    }

}