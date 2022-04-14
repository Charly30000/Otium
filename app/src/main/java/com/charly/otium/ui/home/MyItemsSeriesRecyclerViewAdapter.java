package com.charly.otium.ui.home;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.charly.otium.R;
import com.charly.otium.models.entities.ItemSerieEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyItemsSeriesRecyclerViewAdapter extends RecyclerView.Adapter<MyItemsSeriesRecyclerViewAdapter.ViewHolder> {

    private List<ItemSerieEntity> itemSerieEntityList;
    private List<ItemSerieEntity> originalList;
    private OnItemClickListener listener;

    private HomeViewModel homeViewModel;

    public MyItemsSeriesRecyclerViewAdapter(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        this.itemSerieEntityList = new ArrayList<>();
        this.originalList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = itemSerieEntityList.get(position);

        // TextView
        holder.tvTitle.setText(holder.mItem.getTitle());
        String seasonChapter = String.format("Temporada: %d. Episodio: %d",
                holder.mItem.getSeason(), holder.mItem.getChapter());
        holder.tvSeasonChapter.setText(seasonChapter);
        holder.tvState.setText(holder.mItem.getState());
        holder.tvType.setText(holder.mItem.getType());
        // Button
        holder.btnAddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemSerieEntity its = holder.mItem;
                int lastChapter = its.getChapter();
                its.setChapter(its.getChapter() + 1);
                homeViewModel.updateItemSerieEntity(its);
                notifyDataSetChanged();
                String message = String.format("Aumentado episodio de '%s': %dx%d --> %dx%d",
                        its.getTitle(), its.getSeason(), lastChapter, its.getSeason(), its.getChapter());
                Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        holder.btnSubstractChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemSerieEntity its = holder.mItem;
                int lastChapter = its.getChapter();
                its.setChapter(its.getChapter() - 1);
                homeViewModel.updateItemSerieEntity(its);
                notifyDataSetChanged();
                String message = String.format("Restado episodio de '%s': %dx%d --> %dx%d",
                        its.getTitle(), its.getSeason(), lastChapter, its.getSeason(), its.getChapter());
                Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemSerieEntityList.size();
    }

    public void setItemSeries(List<ItemSerieEntity> itemSerieEntities) {
        this.itemSerieEntityList = itemSerieEntities;
        originalList.addAll(itemSerieEntityList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ItemSerieEntity itemSerieEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void filtered(String findText) {
        int lengthFind = findText.length();
        if (lengthFind == 0) {
            itemSerieEntityList.clear();
            itemSerieEntityList.addAll(originalList);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ItemSerieEntity> collection = itemSerieEntityList.stream()
                        .filter(i -> i.getTitle().toLowerCase().contains(findText.toLowerCase()))
                        .collect(Collectors.toList());
                itemSerieEntityList.clear();
                itemSerieEntityList.addAll(collection);
            } else {
                for (ItemSerieEntity its: originalList) {
                    if (its.getTitle().toLowerCase().contains(findText.toLowerCase())) {
                        itemSerieEntityList.add(its);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSerieEntity mItem;
        public final TextView tvTitle, tvSeasonChapter, tvState, tvType;
        public final Button btnSubstractChapter, btnAddChapter;
        public final CardView cvItemSerie;

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
            // CardView
            cvItemSerie = view.findViewById(R.id.cvItemSerie);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemSerieEntityList.get(position));
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}