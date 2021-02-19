package com.codepath.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepath.flixster.DetailActivity;
import com.codepath.flixster.R;
import com.codepath.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


//Recycler.Adapter is an abstract class and some methods need to filled
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //Context is an abstract class to allow access to certain app classes
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override

    //Inflate the item_movie.xml layout and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onViewCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView) ;
    }

    //Using the holder to bind data into the item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onViewBindViewHolder"+position);
        //position will get the movie at that point
        Movie movie = movies.get(position);
        //bind the movie details into view holder
        holder.bind(movie);


    }
    //Return the total count of items in List
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById((R.id.tvOverview));
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //to check if phone is in landscape
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageUrl=movie.getBackdropPath();
            }
            else{
                imageUrl=movie.getPosterPath();
            }
            //original image
            //Glide.with(context).load(imageUrl).into(ivPoster);

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                //imageUrl=movie.getBackdropPath();
                Glide.with(context)
                        .load(imageUrl)
                        .override(650, 500) // resizes the image to 100x200 pixels but does not respect aspect ratio
                        .into(ivPoster);


            }
            else {Glide.with(context).load(imageUrl).transform(new RoundedCorners(radius)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(ivPoster);}

            //transform(new FitCenter(), new RoundedCornersTransformation(radius, margin));
            
           

            //setting click listener on the entire row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast - like an alternative to debugging, gives a small pop up when item is clicked

                    //this will let us access new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });

        }
    }

    private void transform(FitCenter fitCenter, RoundedCornersTransformation roundedCornersTransformation) {
    }
}
