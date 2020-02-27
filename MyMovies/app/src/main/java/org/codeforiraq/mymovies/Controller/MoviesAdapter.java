package org.codeforiraq.mymovies.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.codeforiraq.mymovies.Model.Movie;
import org.codeforiraq.mymovies.R;
import org.codeforiraq.mymovies.View.DetailsActivity;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    private Context context;


    public MoviesAdapter(Context context,List  movieList) {
        this.moviesList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position ) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.genre.setText(movie.getGenre());
        holder.image.setImageResource(movie.getImage());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView  title,genre, year ;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.mytitle);
            year = itemView.findViewById(R.id.mytitle);
            genre = itemView.findViewById(R.id.genre);
            image = itemView.findViewById(R.id.imageView);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Movie movie = moviesList.get(position);
            Intent intent = new Intent(context , DetailsActivity.class);
            intent.putExtra("title",movie.getTitle());
            intent.putExtra("genre",movie.getGenre());
            intent.putExtra("year",movie.getYear());
            intent.putExtra("image",movie.getImage());
            context.startActivity(intent);
        }
    }
}

