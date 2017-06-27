package net.elshaarawy.smartpan.Data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static net.elshaarawy.smartpan.Data.SmartPanContract.*;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class SmartPanDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "smartpan.db";
    private static final int DATABASE_VERSION = 1;
    public SmartPanDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_COUNTRIES_TABLE = "CREATE TABLE " + COUNTRIES_COLUMNS.C_TABLE_NAME + "( " +
                COUNTRIES_COLUMNS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                COUNTRIES_COLUMNS.C_NAME + " TEXT  , "+
                COUNTRIES_COLUMNS.C_ALPHA2CODE + " TEXT  , "+
                COUNTRIES_COLUMNS.C_CAPITAL + " TEXT  );";

        db.execSQL(CREATE_COUNTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ COUNTRIES_COLUMNS.C_TABLE_NAME);
        this.onCreate(db);
    }

    private interface SQLTableCreator{

    }
}
