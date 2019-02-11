package es.zamorano.popularmovielist.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    private static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w500/";

    @SerializedName("backdrop_path")
    private String backdrop;

    private String overview;

    @SerializedName("poster_path")
    private String poster;

    private String title;

    @SerializedName("vote_average")
    private Float vote;

    public Movie() {
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdrop);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeFloat(vote);
    }

    private Movie(Parcel in) {
        this.backdrop = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.title = in.readString();
        this.vote = in.readFloat();
    }

    public String getBackdrop() {
        return IMAGE_PATH + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String description) {
        this.overview = overview;
    }

    public String getPoster() {
        return IMAGE_PATH + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote(Float vote) {
        this.vote = vote;
    }

    public Float getVote() {
        return vote;
    }

    public static class MovieList {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
