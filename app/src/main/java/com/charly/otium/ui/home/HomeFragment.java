package com.charly.otium.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.charly.otium.AddEditSerieActivity;
import com.charly.otium.R;
import com.charly.otium.databinding.FragmentHomeBinding;
import com.charly.otium.models.entities.ItemSerieEntity;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //---------------------

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        MyItemsSeriesRecyclerViewAdapter adapter = new MyItemsSeriesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        homeViewModel.getAllItemSerieEntity().observe(getActivity(), new Observer<List<ItemSerieEntity>>() {
            @Override
            public void onChanged(List<ItemSerieEntity> itemSerieEntities) {
                adapter.setItemSeries(itemSerieEntities);
            }
        });

        adapter.setOnItemClickListener(new MyItemsSeriesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemSerieEntity itemSerieEntity) {
                Intent intent = new Intent(getContext(), AddEditSerieActivity.class);
                intent.putExtra(AddEditSerieActivity.EXTRA_IS_MODIFYING, true);
                intent.putExtra(AddEditSerieActivity.EXTRA_ID_ITEM_SERIE, itemSerieEntity.getItemSerieId());
                startActivity(intent);
            }
        });
        //---------------------

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}