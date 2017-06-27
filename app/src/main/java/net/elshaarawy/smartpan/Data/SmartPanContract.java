package net.elshaarawy.smartpan.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class SmartPanContract {

    public static final String AUTHORITY = "net.elshaarawy.smartpan";

    public static final Uri BASE_CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();


    public static class COUNTRIES_COLUMNS implements BaseColumns {

        public static final String C_TABLE_NAME = "countries";

        public static final String C_NAME = "c_name";
        public static final String C_ALPHA2CODE = "c_alpha2code";
        public static final String C_CAPITAL = "c_capital";
    }

    public interface Paths {
        String COUNTRIES = "countries";
    }

    public interface ProviderUris {
        Uri CONTETN_URI_COUNTRIES =
                BASE_CONTENT_URI.buildUpon().appendPath(Paths.COUNTRIES).build();
    }

    public interface MatchingCodes {
        int COUNTRIES_DATA = 1001;
    }
    public interface MimeTypes{
        String FORECAST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+ AUTHORITY + "/"+ Paths.COUNTRIES;
    }
}
