package com.example.lab1q2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1q2.provider.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    List<Movie> ds = new ArrayList<>();
    private List<Movie> mMovie;
    public RecyclerAdapter() {}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        Movie movie = ds.get(position);
        holder.title.setText(movie.getMovieD());
        holder.year.setText(movie.getYearD());
        holder.country.setText(movie.getCountryD());
        holder.genre.setText(movie.getGenreD());
        holder.cost.setText(movie.getCostD());
        holder.keyword.setText(movie.getKeywordD());
      //  holder.costUSD.setText(movie.getCostUSDDetails());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
    public void setMovies(List<Movie> movieRecord){this.ds=movieRecord;} {
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        TextView country;
        TextView genre;
        TextView cost;
        TextView keyword;
    //    TextView costUSD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.added_title_id);
            year = itemView.findViewById(R.id.added_year_id);
            country = itemView.findViewById(R.id.added_country_id);
            genre = itemView.findViewById(R.id.added_genre_id);
            cost = itemView.findViewById(R.id.added_cost_id);
            keyword = itemView.findViewById(R.id.added_keywords_id);
          //  costUSD = itemView.findViewById(R.id.added_costUSD_id);

        }


    }
}
