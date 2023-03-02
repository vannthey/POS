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
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragInventoryBinding;

import java.util.List;

public class Frag_inventory extends Fragment {
    FragmentFragInventoryBinding binding;
    List<Inventory> warehouseList;
    Handler handler;
    SharedPreferences sharedPreferences;
    Thread thread;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragInventoryBinding.inflate(inflater, container, false);
        binding.btnCancelInventory.setOnClickListener(this::onCancelAddInventory);
        binding.btnSaveInventory.setOnClickListener(this::onSaveInventory);
        handler = new Handler();
        onCreateMenu();
        onShowAllInventory();
        OnInventoryItemClickListener();
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        thread.interrupt();
        super.onDetach();
    }

    private void onCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_inventory).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_inventory) {
                    binding.txtNoInventoryFound.setVisibility(View.GONE);
                    OnShowAddInventory();
                    OnHideBtnDeleteUpdate();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnInventoryItemClickListener() {
        binding.listInventory.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.inventoryName.setText(warehouseList.get(i).getInventoryName());
            binding.inventoryLocation.setText(warehouseList.get(i).getInventoryAddress());
            OnShowBtnDeleteUpdate();
            OnShowAddInventory();
        });
    }

    private void OnShowBtnDeleteUpdate() {
        binding.btnDeleteInventory.setVisibility(View.VISIBLE);
        binding.btnUpdateInventory.setVisibility(View.VISIBLE);
        binding.btnSaveInventory.setVisibility(View.GONE);
    }

    private void onShowAllInventory() {
        thread = new Thread(() -> {
            warehouseList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllInventory();
            handler.post(() -> {
                if (warehouseList.size() != 0) {
                    binding.txtNoInventoryFound.setVisibility(View.GONE);
                    binding.listInventory.setVisibility(View.VISIBLE);
                    binding.listInventory.setAdapter(new AdapterInventory(warehouseList, requireContext()));
                }
            });
        });
        thread.start();
    }

    private void onSaveInventory(View view) {
        String inventoryName = String.valueOf(binding.inventoryName.getText());
        String inventoryAddress = String.valueOf(binding.inventoryLocation.getText());
        String saveUserLogin = "UserLogin";
        sharedPreferences = requireContext().getSharedPreferences(saveUserLogin, 0);
        String saveUsername = "Username";
        String Username = sharedPreferences.getString(saveUsername, "");
        if (inventoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Inventory Name", Toast.LENGTH_SHORT).show();
        } else {
            Handler handler = new Handler();
           thread = new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao()
                        .createInventory(new Inventory(inventoryName, inventoryAddress, Username,
                                CurrentDateHelper.getCurrentDate()));
                handler.post(() -> {
                    onShowAllInventory();
                    OnClearAllDataInAddInventory();
                });
            });
           thread.start();
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


    private void OnHideBtnDeleteUpdate() {
        binding.btnDeleteInventory.setVisibility(View.GONE);
        binding.btnUpdateInventory.setVisibility(View.GONE);
        binding.btnSaveInventory.setVisibility(View.VISIBLE);
    }

    private void OnShowAddInventory() {
        binding.listInventory.setVisibility(View.GONE);
        binding.layoutAddInventory.setVisibility(View.VISIBLE);
    }

}
