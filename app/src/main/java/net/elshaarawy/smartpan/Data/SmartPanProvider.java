package net.elshaarawy.smartpan.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static net.elshaarawy.smartpan.Data.SmartPanContract.*;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class SmartPanProvider extends ContentProvider {
    private SmartPanDbHelper mDbHelper;
    static UriMatcher sMatcher;
    @Override
    public boolean onCreate() {

        mDbHelper = new SmartPanDbHelper(getContext());
        sMatcher = SmartPanMatcher();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sMatcher.match(uri)) {
            case MatchingCodes.COUNTRIES_DATA:
                cursor = sqLiteDatabase.query(COUNTRIES_COLUMNS.C_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);

        }
        if (cursor != null) {
            //set the uri that cursor will be notifies through it
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        } else throw new SQLiteException("Unsupported Operation");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sMatcher.match(uri)) {
            case MatchingCodes.COUNTRIES_DATA:
                return MimeTypes.FORECAST_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (selection == null) {
            throw new IllegalArgumentException("You shouldn't pass null, you will delete all the table entries if you want that pass empty string \"\"");
        }
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int effectedRows;
        switch (sMatcher.match(uri)) {
            case MatchingCodes.COUNTRIES_DATA:
                effectedRows = sqLiteDatabase.delete(COUNTRIES_COLUMNS.C_TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri.toString());
        }
        if (effectedRows == 1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        sqLiteDatabase.close();
        return effectedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case MatchingCodes.COUNTRIES_DATA:
                return multiInsertion(sqLiteDatabase,
                        COUNTRIES_COLUMNS.C_TABLE_NAME,
                        values,
                        getContext().getContentResolver(),
                        uri);
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        mDbHelper.close();
    }

    private static UriMatcher SmartPanMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, Paths.COUNTRIES, MatchingCodes.COUNTRIES_DATA);

        return uriMatcher;
    }

    private static int multiInsertion(SQLiteDatabase db, String tableName, ContentValues[] values, ContentResolver resolver, Uri _uri) {
        int insertedRows = 0;
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                Long _id = db.insert(tableName, null, value);
                if (_id != -1)
                    insertedRows++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        if (insertedRows > 0) {
            resolver.notifyChange(_uri, null);
        }
        return insertedRows;
    }
}
