package com.example.lab1q2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;

public class MovieContentProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY="fit2081.app.ARNAV";

    MovieDatabase db; //reference to database
    public static final Uri CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

/*private static final int MOVIES=10;
private static final int MOVIE_ID=11;
private static final int MOVIE_NAME=12;


private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);


 static{
        sUriMatcher.addURI(CONTENT_AUTHORITY,"movies",MOVIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY,"movies/#",MOVIE_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY,"movies/*",MOVIE_NAME);


 }
* */


    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
       // throw new UnsupportedOperationException("Not yet implemented");
        int deletionCount;

        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(Movie.MOVIE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) { //authority received in uri
/*int match =sURIMatcher.match(uri);
        switch(match){
            case STUDENTS:
                break;
            case STUDENT_ID:
                uri.getLastPathSegment();
                break;
            default:
                return null;
        }*/
        long newID = db.getOpenHelper().getWritableDatabase().insert(Movie.MOVIE_NAME,0,values);
        return ContentUris.withAppendedId(CONTENT_URI, newID);
    }

    @Override
    public boolean onCreate() {
        db=MovieDatabase.getMovieDB(getContext()); //initialised db
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, //cursor is a special datastructure to hold 1,0 or more rows
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
        builder.setTables(Movie.MOVIE_NAME);
        String query=builder.buildQuery(projection,selection,null,null,sortOrder,null);
        Cursor cursor = db.getOpenHelper().getReadableDatabase().query(query, selectionArgs);
        return (cursor);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
       // throw new UnsupportedOperationException("Not yet implemented");
        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(Movie.MOVIE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}