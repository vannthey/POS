package com.example.pos.unit;

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
import com.example.pos.Database.Entity.Unit;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragUnitBinding;

public class Frag_unit extends Fragment {
    FragmentFragUnitBinding binding;
    Handler handler;
    int unitId;
    UnitViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragUnitBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnSaveUnit.setOnClickListener(this::OnSaveUnit);
        binding.btnCancelUnit.setOnClickListener(this::OnCancelUnit);
        binding.btnDeleteUnit.setOnClickListener(this::OnDeleteUnit);
        binding.btnUpdateUnit.setOnClickListener(this::OnUpdateUnit);
        viewModel = new ViewModelProvider(this).get(UnitViewModel.class);
        OnShowAllUnit();
        OnCreateMenu();
        return binding.getRoot();
    }


    private void OnUpdateUnit(View view) {
        if (binding.unitTitle.getText() != null) {
            new Thread(() -> {
                viewModel.updateUnitById(String.valueOf(binding.unitTitle.getText()), unitId);
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnDeleteUnit(View view) {
        new Thread(() -> {
            viewModel.deleteUnitById(unitId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void OnCancelUnit(View view) {
        OnUpdateUI();
    }

    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_unit).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_unit) {
                    binding.txtNoUnitFound.setVisibility(View.GONE);
                    binding.unitTitle.setText(null);
                    OnShowAddUnit();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnSaveUnit(View view) {
        if (binding.unitTitle.getText() != null) {
            new Thread(() -> {
                viewModel.createUnit(new Unit(String.valueOf(binding.unitTitle.getText()), SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate()));
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnShowAllUnit() {
        viewModel.getAllUnit().observe(getViewLifecycleOwner(), unitList -> {
            if (unitList.size() != 0) {
                binding.txtNoUnitFound.setVisibility(View.GONE);
                binding.listUnit.setVisibility(View.VISIBLE);
                binding.listUnit.setAdapter(new AdapterUnit(unitList, requireContext()));
            }
            binding.listUnit.setOnItemClickListener((adapterView, view, i, l) -> {
                unitId = unitList.get(i).getUnitId();
                binding.unitTitle.setText(unitList.get(i).unitTitle);
                OnShowAddUnit();
                OnShowDeleteUpdate();
            });
        });
    }

    private void OnShowDeleteUpdate() {
        binding.btnSaveUnit.setVisibility(View.GONE);
        binding.btnUpdateUnit.setVisibility(View.VISIBLE);
        binding.btnDeleteUnit.setVisibility(View.VISIBLE);
    }


    private void OnShowAddUnit() {
        binding.listUnit.setVisibility(View.GONE);
        binding.layoutAddUnit.setVisibility(View.VISIBLE);
    }

    private void OnUpdateUI() {
        binding.listUnit.setVisibility(View.VISIBLE);
        binding.layoutAddUnit.setVisibility(View.GONE);
        binding.btnSaveUnit.setVisibility(View.VISIBLE);
        binding.btnUpdateUnit.setVisibility(View.GONE);
        binding.btnDeleteUnit.setVisibility(View.GONE);
        binding.unitTitle.setText(null);
    }
}