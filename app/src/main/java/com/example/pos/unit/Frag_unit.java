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

import com.example.pos.Database.Entity.Unit;
import com.example.pos.DateHelper;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
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
        binding.formUnit.btnSaveUnit.setOnClickListener(this::SaveUnit);
        binding.formUnit.btnCancelUnit.setOnClickListener(this::CancelUnit);
        binding.formUnit.btnDeleteUnit.setOnClickListener(this::DeleteUnit);
        binding.formUnit.btnUpdateUnit.setOnClickListener(this::UpdateUnit);
        viewModel = new ViewModelProvider(this).get(UnitViewModel.class);
        GetAllProduct();
        OnCreateMenu();
        return binding.getRoot();
    }


    private void UpdateUnit(View view) {
        if (binding.formUnit.unitTitle.getText() != null) {
            viewModel.updateUnitById(String.valueOf(binding.formUnit.unitTitle.getText()), unitId);
            OnUpdateUI();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void DeleteUnit(View view) {
        viewModel.deleteUnitById(unitId);
        OnUpdateUI();
    }

    private void SaveUnit(View view) {
        if (!String.valueOf(binding.formUnit.unitTitle.getText()).isEmpty()) {
            viewModel.createUnit(new Unit(String.valueOf(binding.formUnit.unitTitle.getText()), SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()), DateHelper.getCurrentDate()));
            OnUpdateUI();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void CancelUnit(View view) {
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
                    LayoutSaveUnit();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void GetAllProduct() {
        viewModel.getAllUnit().observe(getViewLifecycleOwner(), unitList -> {
            if (unitList.size() != 0) {
                binding.txtNoUnitFound.setVisibility(View.GONE);
                binding.listUnit.setVisibility(View.VISIBLE);
                binding.listUnit.setAdapter(new AdapterUnit(unitList, requireContext()));
            }
            binding.listUnit.setOnItemClickListener((adapterView, view, i, l) -> {
                unitId = unitList.get(i).getUnitId();
                binding.formUnit.unitTitle.setText(unitList.get(i).unitTitle);
                LayoutSaveUnit();
                DeleteAndUpdate();
            });
        });
    }

    private void DeleteAndUpdate() {
        binding.formUnit.btnSaveUnit.setVisibility(View.GONE);
        binding.formUnit.btnUpdateUnit.setVisibility(View.VISIBLE);
        binding.formUnit.btnDeleteUnit.setVisibility(View.VISIBLE);
    }


    private void LayoutSaveUnit() {
        binding.listUnit.setVisibility(View.GONE);
        binding.formUnit.layoutAddUnit.setVisibility(View.VISIBLE);
    }

    private void OnUpdateUI() {
        binding.listUnit.setVisibility(View.VISIBLE);
        binding.formUnit.layoutAddUnit.setVisibility(View.GONE);
        binding.formUnit.btnSaveUnit.setVisibility(View.VISIBLE);
        binding.formUnit.btnUpdateUnit.setVisibility(View.GONE);
        binding.formUnit.btnDeleteUnit.setVisibility(View.GONE);
        binding.formUnit.unitTitle.setText(null);
    }
}