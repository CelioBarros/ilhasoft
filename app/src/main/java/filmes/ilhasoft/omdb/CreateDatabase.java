package filmes.ilhasoft.omdb;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {
    private static final String NAME_DB = "mymovies.db";
    protected static final String TABLE = "movies";
    protected static final String ID = "id";
    protected static final String TITLE = "title";
    protected static final String YEAR = "year";
    protected static final String RUNTIME = "runtime";
    protected static final String GENRE = "genre";
    protected static final String PLOT = "plot";
    protected static final String AWARDS = "awards";
    protected static final String RATING = "rating";
    protected static final String VOTES = "votes";
    protected static final String POSTER = "poster";
    private static final int VERSION = 1;

    public CreateDatabase(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE+"("
                + ID + " TEXT primary key,"
                + TITLE + " TEXT,"
                + YEAR + " INTEGER,"
                + RUNTIME + " TEXT,"
                + GENRE + " TEXT,"
                + PLOT + " TEXT,"
                + AWARDS + " TEXT,"
                + RATING + " REAL,"
                + VOTES + " TEXT,"
                + POSTER + " BLOB"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
