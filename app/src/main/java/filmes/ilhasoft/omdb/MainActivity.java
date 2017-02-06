package filmes.ilhasoft.omdb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
                            searchStr = searchMovie.execute(String.valueOf(v.getText())).get();
                            JSONObject search = new JSONObject(searchStr);
                            JSONArray searchArray = (JSONArray) search.get("Search");
                            final String[] movies = new String[searchArray.length()];
                            String[] posters = new String[searchArray.length()];
                            for (int i = 0; i < searchArray.length(); i++) {
                                movies[i] = (String) ((JSONObject) searchArray.get(i)).get("Title");
                                posters[i] = (String) ((JSONObject) searchArray.get(i)).get("Poster");
                            }
                            CustomList adapter = new
                                    CustomList(MainActivity.this, movies, posters);
                            ListView lv = (ListView)findViewById(R.id.list_search_movies);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(MainActivity.this, "You Clicked at "+movies[position], Toast.LENGTH_LONG).show();

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
