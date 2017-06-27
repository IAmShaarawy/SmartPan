package net.elshaarawy.smartpan.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import net.elshaarawy.smartpan.R;

/**
 * Created by elshaarawy on 27-Jun-17.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    Context mContext;

    public CustomInfoWindow(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView textView = new TextView(mContext);
        textView.setText(marker.getTitle());
        textView.setPadding(4,4,4,4);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        return textView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
