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
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.DateHelper;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
import com.example.pos.databinding.FragmentFragInventoryBinding;

public class Frag_inventory extends Fragment {
    FragmentFragInventoryBinding binding;
    InventoryViewModel inventoryViewModel;
    Handler handler;
    String inventoryName;
    String inventoryAddress;
    int inventoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        handler = new Handler();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragInventoryBinding.inflate(inflater, container, false);
        binding.btnCancelInventory.setOnClickListener(this::CancelInventory);
        binding.btnSaveInventory.setOnClickListener(this::SaveInventory);
        binding.btnDeleteInventory.setOnClickListener(this::DeleteInventory);
        binding.btnUpdateInventory.setOnClickListener(this::UpdateInventory);
        onCreateMenu();
        GetAllInventory();
        return binding.getRoot();
    }

    private void GetAllInventory() {
        inventoryViewModel.getAllInventory().observe(getViewLifecycleOwner(), inventories -> {
            if (inventories.size() != 0) {
                binding.txtNoInventoryFound.setVisibility(View.GONE);
                binding.listInventory.setVisibility(View.VISIBLE);
                binding.listInventory.setAdapter(new AdapterInventory(inventories, requireContext()));
            }
            binding.listInventory.setOnItemClickListener((adapterView, view, i, l) -> {
                inventoryId = inventories.get(i).inventoryId;
                binding.inventoryName.setText(inventories.get(i).getInventoryName());
                binding.inventoryLocation.setText(inventories.get(i).getInventoryAddress());
                DeleteAndUpdate();
                LayoutSaveInventory();
            });
        });
    }

    private void CancelInventory(View view) {
        OnUpdateUI();
    }

    private void UpdateInventory(View view) {
        GetDataFromView();
        new Thread(() -> {
            inventoryViewModel.updateInventoryById(inventoryAddress, inventoryName, inventoryId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void DeleteInventory(View view) {
        new Thread(()->{
            inventoryViewModel.deleteInventoryById(inventoryId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void SaveInventory(View view) {
        GetDataFromView();
        if (inventoryName.isEmpty()) {
            Toast.makeText(requireContext(), "Please Input Inventory Name", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                inventoryViewModel.createInventory(new Inventory(inventoryName, inventoryAddress,
                        SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                        DateHelper.getCurrentDate()));
                handler.post(this::OnUpdateUI);
            }).start();
        }
    }

    private void GetDataFromView() {
        inventoryName = String.valueOf(binding.inventoryName.getText());
        inventoryAddress = String.valueOf(binding.inventoryLocation.getText());
    }

    private void OnUpdateUI() {
        binding.btnDeleteInventory.setVisibility(View.GONE);
        binding.btnUpdateInventory.setVisibility(View.GONE);
        binding.btnSaveInventory.setVisibility(View.VISIBLE);
        binding.listInventory.setVisibility(View.VISIBLE);
        binding.inventoryName.setText("");
        binding.inventoryLocation.setText("");
        binding.layoutAddInventory.setVisibility(View.GONE);
    }


    private void LayoutSaveInventory() {
        binding.listInventory.setVisibility(View.GONE);
        binding.layoutAddInventory.setVisibility(View.VISIBLE);
    }

    private void DeleteAndUpdate() {
        binding.btnDeleteInventory.setVisibility(View.VISIBLE);
        binding.btnUpdateInventory.setVisibility(View.VISIBLE);
        binding.btnSaveInventory.setVisibility(View.GONE);
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
                    LayoutSaveInventory();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

}
