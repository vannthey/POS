package com.example.pos.inventory;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.Database.Entity.Warehouse;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragInventoryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Frag_inventory extends Fragment {
    FragmentFragInventoryBinding binding;
    List<Warehouse> warehouseList;
    AdapterInventory adapterInventory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragInventoryBinding.inflate(inflater, container, false);

        binding.btnCancelInventory.setOnClickListener(this::OnCancelAddInventory);
        binding.btnSaveInventory.setOnClickListener(this::OnSaveInventory);

        onShowAllInventory();

        return binding.getRoot();
    }

    private void onShowAllInventory() {
        Handler handler = new Handler();
        new Thread(() -> {
            warehouseList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();
            handler.post(() -> {
                binding.gridInventory.setAdapter(new AdapterInventory(warehouseList, requireContext()));
            });
        }).start();

    }

    private void OnSaveInventory(View view) {
        String inventoryName = binding.inventoryName.getText().toString();
        if (inventoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Inventory Name", Toast.LENGTH_SHORT).show();
        } else {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",
                    Locale.getDefault());
            String date = simpleDateFormat.format(c);
            Handler handler = new Handler();
            new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createInventory(new Warehouse(inventoryName, date));
            handler.post(()->{
                onShowAllInventory();
                binding.gridInventory.setVisibility(View.VISIBLE);
                binding.inventoryName.setText("");
                binding.layoutAddInventory.setVisibility(View.GONE);
            });
            }).start();
        }
    }

    private void OnCancelAddInventory(View view) {
        binding.gridInventory.setVisibility(View.VISIBLE);
        binding.inventoryName.setText("");
        binding.layoutAddInventory.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_inventory) {
            binding.gridInventory.setVisibility(View.GONE);
            binding.layoutAddInventory.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_inventory).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
