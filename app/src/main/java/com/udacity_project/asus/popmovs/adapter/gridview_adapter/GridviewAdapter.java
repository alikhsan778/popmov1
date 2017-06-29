package com.udacity_project.asus.popmovs.adapter.gridview_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.udacity_project.asus.popmovs.itemobject.ItemObjectMovies;
import com.udacity_project.asus.popmovs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 6/24/2017.
 * Developer is Alikhsan for proyek 1 Pouplar Movies Udacity Nanodegree fasttrack
 */

public class GridviewAdapter extends BaseAdapter {
    private final Context context;
    private List<ItemObjectMovies> urls = new ArrayList<>();
    public GridviewAdapter(Context context, List<ItemObjectMovies> urls){
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public ItemObjectMovies getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_poster_movie,parent,false);
        }
        ImageView imageViewPoster = (ImageView)convertView.findViewById(R.id.image_poster);
        ItemObjectMovies movies = getItem(position);
        Picasso.with(context).load(movies.getPoster_path())
                .placeholder(R.drawable.ic_sync_pic)
                .error(R.drawable.ic_warning_pic)
                .fit()
                .tag(context)
                .into(imageViewPoster);




        return convertView;
    }
}
