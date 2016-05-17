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
 * Created by anuragmaravi on 20/04/16.
 */
public class AdapterCredits extends RecyclerView.Adapter<AdapterCredits.ViewHolder> {

    ArrayList<ListItem> listItems;
    Context ctx;


    public AdapterCredits(ArrayList<ListItem> items, Context ctx){
        super();
        this.listItems=items;
        this.ctx=ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,ctx,listItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  listItems.get(position);

        holder.t1.setText(list.getCredits_name());
        holder.t2.setText("as "+list.getCredits_character());
        ImageLoader.getInstance().displayImage(listItems.get(position).getCredits_profile_path(),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView t1;
        public TextView t2;
        public ImageView imageView;
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        Context ctx;


        public ViewHolder(View itemView, Context ctx, ArrayList<ListItem> listItems) {
            super(itemView);
            this.listItems=listItems;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            t1 = (TextView) itemView.findViewById(R.id.credits_name_textView);
            t2 = (TextView) itemView.findViewById(R.id.credits_character_textView);
            imageView=(ImageView) itemView.findViewById(R.id.credits_profile_poster);

        }
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            ListItem listItems=this.listItems.get(position);
            Intent intent = new Intent(this.ctx,ActorProfile.class);
            intent.putExtra("actor_id",listItems.getActor_id());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.ctx.startActivity(intent);
        }

    }

}