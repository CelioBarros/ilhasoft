package filmes.ilhasoft.omdb;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class DatabaseController {
    private CreateDatabase database;
    private SQLiteDatabase db;

    public DatabaseController(Context context){
        database = new CreateDatabase(context);
        db = database.getWritableDatabase();
    }

    public String insert(String id, String title, Integer year, String runtime, String genre,
                         String plot, String awards, Float rating, String votes, Bitmap poster){
        ContentValues values;
        long result;

        values = new ContentValues();
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

        result = db.insert(CreateDatabase.TABLE, null, values);
        db.close();

        if (result ==-1)
            return "Error add movie/ movie already added";
        else
            return "Add movie complete";
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
