package com.charly.otium;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.charly.otium.models.entities.ItemSerieEntity;

import java.util.ArrayList;
import java.util.List;

public class MyItemsSeriesRecyclerViewAdapter extends RecyclerView.Adapter<MyItemsSeriesRecyclerViewAdapter.ViewHolder> {

    private List<ItemSerieEntity> mValues;

    public MyItemsSeriesRecyclerViewAdapter(/*Context context, List<ItemSerieEntity> items*/) {
        mValues = new ArrayList<>();
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

        holder.tvTitle.setText(holder.mItem.getTitle());
        String seasonChapter = String.format("Temporada: %d. Episodio: %d",
                holder.mItem.getSeason(), holder.mItem.getChapter());
        holder.tvSeasonChapter.setText(seasonChapter);
        holder.tvState.setText(holder.mItem.getState());
        holder.tvType.setText(String.valueOf(holder.mItem.getTypeId()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setItemSeries(List<ItemSerieEntity> itemSerieEntities) {
        this.mValues = itemSerieEntities;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSerieEntity mItem;
        public final TextView tvTitle, tvSeasonChapter, tvState, tvType;
        public final Button btnSubstractChapter, btnAddChapter;

        public ViewHolder(View view) {
            super(view);
            // TextView
            tvTitle = view.findViewById(R.id.tvTitle);
            tvSeasonChapter = view.findViewById(R.id.tvSeasonChapter);
            tvState = view.findViewById(R.id.tvState);
            tvType = view.findViewById(R.id.tvType);
            // Button
            btnSubstractChapter = view.findViewById(R.id.btnSubstractChapter);
            btnAddChapter = view.findViewById(R.id.btnAddChapter);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}