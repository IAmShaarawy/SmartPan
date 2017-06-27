package net.elshaarawy.smartpan.Adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.elshaarawy.smartpan.R;

import java.util.List;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private List<Pair<String, String>> mContactsPairList;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, List<Pair<String, String>> contactsPairList) {
        this.mContactsPairList = contactsPairList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Pair<String, String> contactPair = mContactsPairList.get(position);
        holder.name.setText(contactPair.first);
        holder.number.setText(contactPair.second);
    }

    @Override
    public int getItemCount() {
        return mContactsPairList.size();
    }
    public void swipData(List<Pair<String, String>> contactsPairList){
        mContactsPairList.clear();
        mContactsPairList.addAll(contactsPairList);
        this.notifyDataSetChanged();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView name;
        TextView number;

        ContactHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.contact_item);
            name = (TextView) itemView.findViewById(R.id.contact_name);
            number = (TextView) itemView.findViewById(R.id.contact_number);
        }

        public LinearLayout getContainer() {
            return container;
        }

        public TextView getName() {
            return name;
        }

        public TextView getNumber() {
            return number;
        }
    }
}
