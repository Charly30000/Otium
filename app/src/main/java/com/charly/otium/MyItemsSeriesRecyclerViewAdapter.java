package com.charly.otium;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charly.otium.models.entities.ItemSerieEntity;

import java.util.List;

public class MyItemsSeriesRecyclerViewAdapter extends RecyclerView.Adapter<MyItemsSeriesRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<ItemSerieEntity> mValues;

    public MyItemsSeriesRecyclerViewAdapter(Context context, List<ItemSerieEntity> items) {
        ctx = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.textViewTitulo.setText(holder.mItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSerieEntity mItem;
        public final TextView textViewTitulo;

        public ViewHolder(View view) {
            super(view);
            textViewTitulo = view.findViewById(R.id.textViewTitulo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewTitulo.getText() + "'";
        }
    }
}