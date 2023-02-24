package com.example.pos.inventory;

import android.content.SharedPreferences;
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

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragInventoryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Frag_inventory extends Fragment {
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUsername = "Username";
    FragmentFragInventoryBinding binding;
    List<Inventory> warehouseList;
    Handler handler;
    SharedPreferences sharedPreferences;

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

        binding.btnCancelInventory.setOnClickListener(this::onCancelAddInventory);
        binding.btnSaveInventory.setOnClickListener(this::onSaveInventory);

        onShowAllInventory();
        OnInventoryItemClickListener();
        return binding.getRoot();
    }

    private void OnInventoryItemClickListener() {
        binding.listInventory.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.inventoryName.setText(warehouseList.get(i).getInventoryName());
            binding.inventoryLocation.setText(warehouseList.get(i).getInventoryAddress());
            OnSHowBtnDeleteUpdate();
            OnShowAddInventory();
        });
    }

    private void OnSHowBtnDeleteUpdate() {
        binding.btnDeleteInventory.setVisibility(View.VISIBLE);
        binding.btnUpdateInventory.setVisibility(View.VISIBLE);
        binding.btnSaveInventory.setVisibility(View.GONE);
    }

    private void onShowAllInventory() {
        handler = new Handler();
        new Thread(() -> {
            warehouseList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();
            handler.post(() -> {
                binding.listInventory.setAdapter(new AdapterInventory(warehouseList, requireContext()));
            });
        }).start();

    }

    private void onSaveInventory(View view) {
        String inventoryName = String.valueOf(binding.inventoryName.getText());
        String inventoryAddress = String.valueOf(binding.inventoryLocation.getText());
        sharedPreferences = requireContext().getSharedPreferences(SaveUserLogin, 0);
        String Username = sharedPreferences.getString(SaveUsername, "");
        if (inventoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Inventory Name", Toast.LENGTH_SHORT).show();
        } else {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",
                    Locale.getDefault());
            String date = simpleDateFormat.format(c);
            Handler handler = new Handler();
            new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao()
                        .createInventory(new Inventory(inventoryName, inventoryAddress, Username, date));
                handler.post(() -> {
                    onShowAllInventory();
                    OnClearAllDataInAddInventory();
                });
            }).start();
        }
    }

    private void onCancelAddInventory(View view) {
        OnClearAllDataInAddInventory();
    }

    private void OnClearAllDataInAddInventory() {
        binding.listInventory.setVisibility(View.VISIBLE);
        binding.inventoryName.setText("");
        binding.inventoryLocation.setText("");
        binding.layoutAddInventory.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_inventory) {
            OnShowAddInventory();
            OnHideBtnDeleteUpdate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void OnHideBtnDeleteUpdate() {
        binding.btnDeleteInventory.setVisibility(View.GONE);
        binding.btnUpdateInventory.setVisibility(View.GONE);
        binding.btnSaveInventory.setVisibility(View.VISIBLE);
    }

    private void OnShowAddInventory() {
        binding.listInventory.setVisibility(View.GONE);
        binding.layoutAddInventory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_inventory).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
