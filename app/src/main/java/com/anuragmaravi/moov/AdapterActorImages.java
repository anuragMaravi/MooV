package com.anuragmaravi.moov;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by anuragmaravi on 16/05/16.
 */
public class AdapterActorImages extends RecyclerView.Adapter<AdapterActorImages.ViewHolder> {

    ArrayList<ListItem> listItems;
    Context ctx;


    public AdapterActorImages(ArrayList<ListItem> items, Context ctx){
        super();
        this.listItems=items;
        this.ctx=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  listItems.get(position);

        ImageLoader.getInstance().displayImage(listItems.get(position).getActor_image_paths(),holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.upcoming_poster_imageView);

        }

    }


}
