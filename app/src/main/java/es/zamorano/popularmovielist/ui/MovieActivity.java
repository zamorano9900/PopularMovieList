package es.zamorano.popularmovielist.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.zamorano.popularmovielist.R;
import es.zamorano.popularmovielist.models.Movie;
import io.realm.Realm;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.movie_activity_poster)
    ImageView mMoviePoster;
    @BindView(R.id.movie_activity_title)
    TextView mMovieTittle;
    @BindView(R.id.movie_activity_overview)
    TextView mMovieOverview;
    @BindView(R.id.movie_activity_vote)
    TextView mMovieVote;
    @BindView(R.id.fab_movie_activity)
    FloatingActionButton mFab;

    private Movie mMovieDatabase;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();

        final Movie mMovie;
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

        mMovieDatabase = mRealm.where(Movie.class).equalTo("id", mMovie.getId()).findFirst();

        if (mMovieDatabase != null)
            mFab.setImageResource(R.drawable.ic_favorite);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMovieDatabase != null) {
                    mRealm.beginTransaction();
                    mMovieDatabase.deleteFromRealm();
                    mRealm.commitTransaction();
                    mFab.setImageResource(R.drawable.ic_favorite_border);

                } else {
                    mRealm.beginTransaction();
                    mMovieDatabase = mMovie;
                    mRealm.insert(mMovieDatabase);
                    mRealm.commitTransaction();
                    mFab.setImageResource(R.drawable.ic_favorite);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
