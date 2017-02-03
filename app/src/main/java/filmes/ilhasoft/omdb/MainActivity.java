package filmes.ilhasoft.omdb;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*

        setListAdapter(new ArrayAdapter<String>(
                this, R.layout.list_movies,
                R.id.Itemname,itemname));
*/

        
        EditText editText = (EditText) findViewById(R.id.editTextMovie);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchMovie searchMovie = new SearchMovie();
                    String searchStr = "";
                    try {
                        searchStr = searchMovie.execute(String.valueOf(v.getText())).get();
                        JSONObject search = new JSONObject(searchStr);
                        JSONArray searchArray = (JSONArray) search.get("Search");
                        String[] movies = new String[searchArray.length()];
                        for (int i = 0; i < searchArray.length(); i++) {
                            movies[i] = (String) ((JSONObject) searchArray.get(i)).get("Title");
                        }
                        ListView lv = (ListView)findViewById(R.id.list);
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(), R.layout.list_movies, R.id.Itemname, movies);
                        lv.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    return true;
                }
                return false;
            }
        });
    }

}
