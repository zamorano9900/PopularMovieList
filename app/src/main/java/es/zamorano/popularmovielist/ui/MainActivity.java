package es.zamorano.popularmovielist.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.zamorano.popularmovielist.R;
import es.zamorano.popularmovielist.models.Movie;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;
    public static final String API_KEY = "b66ffea8276ce576d60df52600822c88";
    private MoviesAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mMovieAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        loadMovies();
    }

    public void loadMovies() {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=" + Resources.getSystem().getConfiguration().locale.getLanguage())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Movie.MovieList responseArray = gson.fromJson(response.toString(), Movie.MovieList.class);
                        //In the responseArray, we have only the first page of the films, in the future, could be nice add an OnScrollListener and load the next page
                        mMovieAdapter.setMovieList(responseArray.getResults());
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
}
