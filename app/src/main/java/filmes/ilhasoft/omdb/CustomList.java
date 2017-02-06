package filmes.ilhasoft.omdb;

/**
 * Created by Celio on 06/02/2017.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.R.attr.bitmap;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] posters;
    public CustomList(Activity context, String[] web, String[] posters) {
        super(context, R.layout.list_movies, web);
        this.context = context;
        this.web = web;
        this.posters = posters;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_movies, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.movie_name);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.movie_poster);
        txtTitle.setText(web[position]);

/*
        GetPoster poster = new GetPoster();
        InputStream posterInput = null;
        try {
            posterInput = poster.execute(position).get();
            Bitmap bitmap = BitmapFactory.decodeStream(posterInput);
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/

        try {
            GetPoster poster = new GetPoster();
            imageView.setImageBitmap(poster.execute(position).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowView;
    }


    private class GetPoster extends AsyncTask<Integer, Void, Bitmap> {

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