package net.elshaarawy.smartpan.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.elshaarawy.smartpan.Data.Entities.CountryEntity;
import net.elshaarawy.smartpan.R;

import java.util.List;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryHolder> {

    private List<CountryEntity> mCountriesList;

    public CountriesAdapter(List<CountryEntity> mCountriesList) {

        this.mCountriesList = mCountriesList;
    }

    @Override
    public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_country,parent,false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryHolder holder, int position) {
        CountryEntity country = mCountriesList.get(position);
        Uri uri = Uri.parse("http://www.geognos.com/api/en/countries/flag/")
                .buildUpon()
                .appendPath(country.getAlpha2Code()+".png")
                .build();
        Picasso.with(holder.itemView.getContext())
                .load(uri)
                .into(holder.flag);
        holder.name.setText(country.getName());
        holder.capital.setText(country.getCapital());
    }

    @Override
    public int getItemCount() {
        return mCountriesList.size();
    }

    public void swipData(List<CountryEntity> countryEntities){
        mCountriesList.clear();
        mCountriesList.addAll(countryEntities);
        this.notifyDataSetChanged();
    }

    public class CountryHolder extends RecyclerView.ViewHolder{
        CardView item;
        ImageView flag;
        TextView name,capital;
        public CountryHolder(View itemView) {
            super(itemView);

            item = (CardView) itemView.findViewById(R.id.country_item);
            flag = (ImageView) itemView.findViewById(R.id.flag);
            name = (TextView) itemView.findViewById(R.id.country_name);
            capital = (TextView) itemView.findViewById(R.id.country_capital);

        }
    }

}
