package com.example.pos.inventory;

import android.os.Bundle;
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

import com.example.pos.Database.Entity.Inventory;
import com.example.pos.DateHelper;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
import com.example.pos.databinding.FragmentFragInventoryBinding;

public class Frag_inventory extends Fragment {
    FragmentFragInventoryBinding binding;
    InventoryViewModel inventoryViewModel;
    String inventoryName;
    String inventoryAddress;
    int inventoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragInventoryBinding.inflate(inflater, container, false);
        binding.formInventory.btnCancelInventory.setOnClickListener(this::CancelInventory);
        binding.formInventory.btnSaveInventory.setOnClickListener(this::SaveInventory);
        binding.formInventory.btnDeleteInventory.setOnClickListener(this::DeleteInventory);
        binding.formInventory.btnUpdateInventory.setOnClickListener(this::UpdateInventory);
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
                binding.formInventory.inventoryName.setText(inventories.get(i).getInventoryName());
                binding.formInventory.inventoryLocation.setText(inventories.get(i).getInventoryAddress());
                DeleteAndUpdate();
                LayoutSaveInventory();
            });
        });
    }

    private void CancelInventory(View view) {
        OnUpdateUI();
    }

    private void UpdateInventory(View view) {
        if (String.valueOf(binding.formInventory.inventoryName.getText()).isEmpty()
                || String.valueOf(binding.formInventory.inventoryLocation.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Inventory, Toast.LENGTH_SHORT).show();
        } else {
            inventoryName = String.valueOf(binding.formInventory.inventoryName.getText());
            inventoryAddress = String.valueOf(binding.formInventory.inventoryLocation.getText());

            inventoryViewModel.updateInventoryById(inventoryAddress, inventoryName, inventoryId);
            OnUpdateUI();
        }

    }

    private void DeleteInventory(View view) {
        inventoryViewModel.deleteInventoryById(inventoryId);
        OnUpdateUI();
    }

    private void SaveInventory(View view) {
        if (String.valueOf(binding.formInventory.inventoryName.getText()).isEmpty()
                || String.valueOf(binding.formInventory.inventoryLocation.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Inventory, Toast.LENGTH_SHORT).show();
        } else {
            inventoryName = String.valueOf(binding.formInventory.inventoryName.getText());
            inventoryAddress = String.valueOf(binding.formInventory.inventoryLocation.getText());
            inventoryViewModel.createInventory(new Inventory(inventoryName, inventoryAddress,
                    SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                    DateHelper.getCurrentDate()));
            OnUpdateUI();
        }
    }

    private void OnUpdateUI() {
        binding.formInventory.btnDeleteInventory.setVisibility(View.GONE);
        binding.formInventory.btnUpdateInventory.setVisibility(View.GONE);
        binding.formInventory.btnSaveInventory.setVisibility(View.VISIBLE);
        binding.listInventory.setVisibility(View.VISIBLE);
        binding.formInventory.inventoryName.setText("");
        binding.formInventory.inventoryLocation.setText("");
        binding.formInventory.layoutAddInventory.setVisibility(View.GONE);
    }


    private void LayoutSaveInventory() {
        binding.listInventory.setVisibility(View.GONE);
        binding.formInventory.layoutAddInventory.setVisibility(View.VISIBLE);
    }

    private void DeleteAndUpdate() {
        binding.formInventory.btnDeleteInventory.setVisibility(View.VISIBLE);
        binding.formInventory.btnUpdateInventory.setVisibility(View.VISIBLE);
        binding.formInventory.btnSaveInventory.setVisibility(View.GONE);
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
