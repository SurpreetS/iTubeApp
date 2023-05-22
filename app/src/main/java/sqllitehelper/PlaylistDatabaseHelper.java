package sqllitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDatabaseHelper extends SQLiteOpenHelper {

    public PlaylistDatabaseHelper(@Nullable Context context) {
        super(context, PlaylistUtil.DATABASE_NAME, null, PlaylistUtil.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE =
                "CREATE TABLE " + PlaylistUtil.TABLE_NAME + "(" +
                        PlaylistUtil.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PlaylistUtil.COLUMN_LINK + " TEXT" +
                        ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Drop the existing table and recreate it if the database version is updated
        db.execSQL("DROP TABLE IF EXISTS " + PlaylistUtil.TABLE_NAME);
        onCreate(db);
    }


    public long insertLink(PlaylistData playlistData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlaylistUtil.COLUMN_LINK, playlistData.getLink());

        long rowID = db.insert(PlaylistUtil.TABLE_NAME,null ,contentValues);

        return rowID;
    }

    public List<PlaylistData> getPlaylist() {
        List<PlaylistData> playlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PlaylistUtil.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int linkColumnIndex = cursor.getColumnIndex(PlaylistUtil.COLUMN_LINK);

            if (linkColumnIndex != -1) {
                do {
                    String link = cursor.getString(linkColumnIndex);
                    PlaylistData playlistData = new PlaylistData(link);
                    playlist.add(playlistData);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return playlist;
    }



}
