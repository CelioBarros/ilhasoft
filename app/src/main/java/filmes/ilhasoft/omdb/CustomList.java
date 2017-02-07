package filmes.ilhasoft.omdb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] movies;
    private final String[] posters;
    private Bitmap[] posterImg;
    public CustomList(Activity context, String[] movies, String[] posters) {
        super(context, R.layout.list_movies, movies);
        this.context = context;
        this.movies = movies;
        this.posters = posters;
        this.posterImg = new Bitmap[posters.length];
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_movies, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.movie_name);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.movie_poster);
        txtTitle.setText(movies[position]);

        try {
            GetPoster poster = new GetPoster();
            posterImg[position] = poster.execute(position).get();
            imageView.setImageBitmap(posterImg[position]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowView;
    }

    public Bitmap getPosterImg(int position){
        return posterImg[position];
    }


    public class GetPoster extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Integer... args) {

            try {
                String linkPoster = posters[args[0]];
                if(linkPoster=="N/A"){
                    linkPoster = "http://image10.bizrate-images.com/resize?sq=60&uid=2216744464";
                }
                URL url = new URL(linkPoster);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp;
            }catch( Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}