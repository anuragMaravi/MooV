package com.anuragmaravi.moov;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by anuragmaravi on 21/04/16.
 */
public class AdapterSlidingViewpage extends PagerAdapter {
    private int[] images={R.drawable.captain,R.drawable.imageview};
//    Description description;
//    private String[] upcoming_movies_images={description.final_backdrop_path};
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterSlidingViewpage(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(FrameLayout)object );
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.upcoming_viewpager_layout,container,false);
        ImageView imageView=(ImageView) view.findViewById(R.id.viewPagerIv);
        imageView.setImageResource(images[position]);
        //ImageLoader.getInstance().displayImage(images[position], imageView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }
}
