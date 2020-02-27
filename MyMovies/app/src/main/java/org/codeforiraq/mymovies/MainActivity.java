package org.codeforiraq.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.codeforiraq.mymovies.Controller.MoviesAdapter;
import org.codeforiraq.mymovies.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        moviesAdapter = new MoviesAdapter(this,movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);

        MoviesData();
    }

    private void MoviesData() {

        Movie movie = new Movie( "Birds of Prey","Action | Adventure | Crime",
                "2020" , R.drawable.a1 );
        movieList.add(movie);

        Movie movie2 = new Movie( "The Lodge ","Drama | Horror | Thriller",
                "2019",  R.drawable.a2 );
        movieList.add(movie2);
        Movie movie3 = new Movie( "Shikara ","Drama | History | Romance",
                "2020",   R.drawable.a3 );
        movieList.add(movie3);
        Movie movie4 = new Movie( "Malang ","Action | Romance","2020",
                R.drawable.a4 );
        movieList.add(movie4);
        Movie movie5 = new Movie(  "Come to Daddy ","Comedy | Horror | Thriller",
                "2019", R.drawable.a5 );
        movieList.add(movie5);
        Movie movie6 = new Movie( "And Then We Danced ","Drama | Romance",
                "2019",   R.drawable.a6 );
        movieList.add(movie6);
        Movie movie7 = new Movie( "Legend of Deification ","Animation | Family | Fantasy"
                ,"2020", R.drawable.a7 );
        movieList.add(movie7);
        Movie movie8 = new Movie( "Waiting for Anya ","Drama | War","2020",
                R.drawable.a8 );
        movieList.add(movie8);

        moviesAdapter.notifyDataSetChanged();
    }
}
