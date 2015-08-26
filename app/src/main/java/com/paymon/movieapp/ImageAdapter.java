package com.paymon.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> array;
    private int bmwidth;

    public ImageAdapter(Context c, ArrayList<String> arrayParam, int x) {


        mContext = c;
        this.array = arrayParam;
        this.bmwidth = x;
    }

    public int getCount() {
        return array.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }
        Drawable d = resizeDrawable(mContext.getResources().getDrawable(R.drawable.placeholder));


        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + array.get(position)).
                resize(bmwidth, (int) (bmwidth * 1.5)).
                placeholder(d).into(imageView);

        return imageView;

    }
    private Drawable resizeDrawable(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, bmwidth, (int)(bmwidth*1.5), false);
        return new BitmapDrawable(mContext.getResources(), bitmapResized);
    }
}
