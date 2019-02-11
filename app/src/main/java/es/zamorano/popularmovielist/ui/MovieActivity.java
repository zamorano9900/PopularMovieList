package es.zamorano.popularmovielist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.zamorano.popularmovielist.R;
import es.zamorano.popularmovielist.models.Movie;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.movie_activity_poster)
    ImageView mMoviePoster;
    @BindView(R.id.movie_activity_title)
    TextView mMovieTittle;
    @BindView(R.id.movie_activity_overview)
    TextView mMovieOverview;
    @BindView(R.id.movie_activity_vote)
    TextView mMovieVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Movie mMovie;
        if (getIntent().hasExtra("movie")) {
            mMovie = getIntent().getParcelableExtra("movie");
        } else {
            throw new IllegalArgumentException();
        }

        mMovieTittle.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieVote.setText(String.format(getString(R.string.vote_raiting), mMovie.getVote()));

        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .placeholder(R.color.colorPrimary)
                .into(mMoviePoster);
    }
}
