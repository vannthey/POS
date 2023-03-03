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
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Inventory;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragInventoryBinding;

public class Frag_inventory extends Fragment {
    FragmentFragInventoryBinding binding;
    InventoryViewModel inventoryViewModel;
    Handler handler;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragInventoryBinding.inflate(inflater, container, false);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        binding.btnCancelInventory.setOnClickListener(this::onCancelAddInventory);
        binding.btnSaveInventory.setOnClickListener(this::onSaveInventory);
        binding.btnDeleteInventory.setOnClickListener(this::OnDeleteInventory);
        binding.btnUpdateInventory.setOnClickListener(this::OnUpdateInventory);
        handler = new Handler();
        onCreateMenu();
        onShowAllInventory();
        return binding.getRoot();
    }

    private void OnUpdateInventory(View view) {
    }

    private void OnDeleteInventory(View view) {
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
                    binding.btnDeleteInventory.setVisibility(View.GONE);
                    binding.btnUpdateInventory.setVisibility(View.GONE);
                    binding.btnSaveInventory.setVisibility(View.VISIBLE);
                    binding.inventoryLocation.setText(null);
                    binding.inventoryName.setText(null);
                    OnShowAddInventory();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }


    private void OnShowBtnDeleteUpdate() {
        binding.btnDeleteInventory.setVisibility(View.VISIBLE);
        binding.btnUpdateInventory.setVisibility(View.VISIBLE);
        binding.btnSaveInventory.setVisibility(View.GONE);
    }

    private void onShowAllInventory() {
        inventoryViewModel.getAllInventory().observe(getViewLifecycleOwner(), inventories -> {
            if (inventories.size() != 0) {
                binding.txtNoInventoryFound.setVisibility(View.GONE);
                binding.listInventory.setVisibility(View.VISIBLE);
                binding.listInventory.setAdapter(new AdapterInventory(inventories, requireContext()));
            }
            binding.listInventory.setOnItemClickListener((adapterView, view, i, l) -> {
                binding.inventoryName.setText(inventories.get(i).getInventoryName());
                binding.inventoryLocation.setText(inventories.get(i).getInventoryAddress());
                OnShowBtnDeleteUpdate();
                OnShowAddInventory();
            });
        });
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
            new Thread(() -> {
                inventoryViewModel.createInventory(new Inventory(inventoryName, inventoryAddress, Username,
                        CurrentDateHelper.getCurrentDate()));
                handler.post(this::OnUpdateUI);
            }).start();
        }
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

    private void onCancelAddInventory(View view) {
        OnUpdateUI();
    }

    private void OnShowAddInventory() {
        binding.listInventory.setVisibility(View.GONE);
        binding.layoutAddInventory.setVisibility(View.VISIBLE);
    }

}
