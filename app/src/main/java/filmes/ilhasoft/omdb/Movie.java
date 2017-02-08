package filmes.ilhasoft.omdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;

/**
 * Created by Celio on 07/02/2017.
 */

public class Movie {
    private String id;
    private String title;
    private Integer year;
    private String runtime;
    private String genre;
    private String plot;
    private String awards;
    private Float rating;
    private String votes;
    private byte[] poster;

    public Movie(String id, String title, Integer year, String runtime, String genre,
                 String plot, String awards, Float rating, String votes, byte[] poster) {
        this.id=id;
        this.title=title;
        this.year = year;
        this.runtime = runtime;
        this.genre = genre;
        this.plot = plot;
        this.awards = awards;
        this.rating = rating;
        this.votes = votes;
        this.poster = poster;
    }

    public byte[] getPoster() {
        return poster;
    }

    public Bitmap getPosterBmp() {
        return BitmapFactory.decodeByteArray(poster, 0, poster.length);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public Float getRating() {
        return rating;
    }

    public String getVotes() {
        return votes;
    }

}
