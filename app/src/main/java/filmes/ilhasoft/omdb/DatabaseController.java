package filmes.ilhasoft.omdb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private CreateDatabase database;
    private SQLiteDatabase db;
    private SQLiteDatabase dbGet;
    private ContentValues values = new ContentValues();;

    public DatabaseController(Context context){
        database = new CreateDatabase(context);
    }

    public String insert(String id, String title, Integer year, String runtime, String genre,
                         String plot, String awards, Float rating, String votes, Bitmap poster){
        long result = 0;

        values.put(CreateDatabase.ID, id);
        values.put(CreateDatabase.TITLE, title);
        values.put(CreateDatabase.YEAR, year);
        values.put(CreateDatabase.RUNTIME, runtime);
        values.put(CreateDatabase.GENRE, genre);
        values.put(CreateDatabase.PLOT, plot);
        values.put(CreateDatabase.AWARDS, awards);
        values.put(CreateDatabase.RATING, rating);
        values.put(CreateDatabase.VOTES, votes);
        values.put(CreateDatabase.POSTER, getBitmapAsByteArray(poster));

        db = database.getWritableDatabase();
        result = db.insert(CreateDatabase.TABLE, null, values);
        db.close();

        values.clear();

        if (result ==-1) {
            return "Error add movie/ movie already added";
        }
        else {
            return "Add movie complete";
        }
    }

    public List<Movie> getFavoriteMovies(){
        List<Movie> listMovies = new ArrayList<Movie>();
        String selectQuery = "select * from " + CreateDatabase.TABLE;
        dbGet = database.getReadableDatabase();
        Cursor cursor = dbGet.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getFloat(7),cursor.getString(8),
                        cursor.getBlob(9));
                listMovies.add(movie);
            } while (cursor.moveToNext());
        }
        dbGet.close();
        return listMovies;
    }

    public void removeMovie(String id){
        db = database.getWritableDatabase();
        db.execSQL("DELETE FROM " + CreateDatabase.TABLE + " WHERE " + CreateDatabase.ID +" = '"+id + "';");
        db.close();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
