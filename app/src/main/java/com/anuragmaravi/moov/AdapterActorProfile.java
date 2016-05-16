package com.anuragmaravi.moov;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by anuragmaravi on 12/05/16.
 */
public class AdapterActorProfile extends RecyclerView.Adapter<AdapterActorProfile.ViewHolder> {

        ArrayList<ListItem> listItems;
        Context ctx;


public AdapterActorProfile(ArrayList<ListItem> items){
        super();
        this.listItems=items;
        this.ctx=ctx;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.actor_movies_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,ctx,listItems);
        return viewHolder;
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  listItems.get(position);

        holder.t1.setText(list.getCredits_character());
        ImageLoader.getInstance().displayImage(listItems.get(position).getCredits_profile_path(),holder.imageView);

        }

@Override
public int getItemCount() {
        return listItems.size();
        }

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView t1;
    public ImageView imageView;
    ArrayList<ListItem> listItems = new ArrayList<ListItem>();
    Context ctx;

    public ViewHolder(View itemView, Context ctx, ArrayList<ListItem> listItems) {
        super(itemView);
        this.listItems=listItems;
        this.ctx=ctx;
        itemView.setOnClickListener(this);

        t1 = (TextView) itemView.findViewById(R.id.upcoming_release_date_textView);
        imageView=(ImageView) itemView.findViewById(R.id.upcoming_poster_imageView);

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
