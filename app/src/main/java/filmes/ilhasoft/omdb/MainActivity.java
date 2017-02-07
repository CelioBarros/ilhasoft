package filmes.ilhasoft.omdb;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.editTextMovie);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (isOnline()){
                        SearchMovie searchMovie = new SearchMovie(MainActivity.this);
                        String searchStr = "";
                        try {
                            searchStr = searchMovie.execute((String.valueOf(v.getText())).replace(" ", "+")).get();
                            JSONObject search = new JSONObject(searchStr);
                            JSONArray searchArray = (JSONArray) search.get("Search");
                            String[] movies = new String[searchArray.length()];
                            final String[] posters = new String[searchArray.length()];
                            for (int i = 0; i < searchArray.length(); i++) {
                                movies[i] = (String) ((JSONObject) searchArray.get(i)).get("Title");
                                posters[i] = (String) ((JSONObject) searchArray.get(i)).get("Poster");
                            }
                            final CustomList adapter = new
                                    CustomList(MainActivity.this, movies, posters);
                            ListView lv = (ListView)findViewById(R.id.list_search_movies);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    MovieInformation movieInformation = new MovieInformation(MainActivity.this);
                                    String result = "";
                                    try {
                                        String movie = movieInformation.execute("http://www.omdbapi.com/?i=tt0372784").get();
                                        Map<String, String> mapMovie = new Gson().fromJson(movie, new TypeToken<HashMap<String, String>>() {}.getType());
                                        DatabaseController crud = new DatabaseController(getBaseContext());
                                        result = crud.insert(mapMovie.get("imdbID"), mapMovie.get("Title"),
                                                Integer.parseInt(mapMovie.get("Year")), mapMovie.get("Runtime"), mapMovie.get("Genre"),
                                                mapMovie.get("Plot"), mapMovie.get("Awards"), Float.parseFloat(mapMovie.get("imdbRating")),
                                                mapMovie.get("imdbVotes"), adapter.getPosterImg(position));

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(MainActivity.this,result, Toast.LENGTH_LONG).show();
                                    Log.d("teste", result);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    } else {
                        Toast.makeText(MainActivity.this, "Please verify your Network connection!", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
