package com.example.pos.supplier;

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
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragSupplierBinding;

public class Frag_supplier extends Fragment {
    FragmentFragSupplierBinding binding;
    SupplierViewModel viewModel;
    Handler handler;
    String SupplierName;
    String SupplierPhone;
    String SupplierAddress;
    boolean isMale = true;
    boolean isFemale = true;

    String SupplierSex;
    Supplier supplier;
    int supplierId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSupplierBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        handler = new Handler();
        binding.btnCancelSupplier.setOnClickListener(v -> OnUpdateUI());
        binding.btnSaveSupplier.setOnClickListener(this::OnSaveSupplier);
        binding.btnUpdateSupplier.setOnClickListener(this::OnUpdateSupplier);
        binding.btnDeleteSupplier.setOnClickListener(this::OnDeleteSupplier);
        OnCreateMenu();
        OnShowAllSupplier();
        return binding.getRoot();
    }

    private void OnDeleteSupplier(View view) {
        new Thread(() -> {
            viewModel.deleteSupplierById(supplierId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void OnUpdateSupplier(View view) {
        SupplierName = String.valueOf(binding.txtSupplierName.getText());
        SupplierPhone = String.valueOf(binding.txtSupplierPhone.getText());
        SupplierAddress = String.valueOf(binding.txtSupplierAddress.getText());
        if (SupplierName != null) {
            new Thread(() -> {
                viewModel.updateSupplierById(SupplierName, SupplierAddress, SupplierSex, SupplierPhone, supplierId);
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Supplier, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_supplier).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_supplier) {
                    binding.txtNoSupplierFound.setVisibility(View.GONE);
                    binding.btnDeleteSupplier.setVisibility(View.GONE);
                    binding.btnUpdateSupplier.setVisibility(View.GONE);
                    binding.supplierMale.setChecked(false);
                    binding.supplierFemale.setChecked(false);
                    binding.txtSupplierPhone.setText(null);
                    binding.txtSupplierAddress.setText(null);
                    binding.txtSupplierName.setText(null);
                    OnShowAddSupplier();
                    OnCheckSupplierSex();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnShowDeleteUpdate() {
        binding.btnDeleteSupplier.setVisibility(View.VISIBLE);
        binding.btnUpdateSupplier.setVisibility(View.VISIBLE);
        binding.btnSaveSupplier.setVisibility(View.GONE);
    }

    private void OnCheckSupplierSex() {
        binding.supplierMale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isMale == binding.supplierMale.isChecked()) {
                SupplierSex = "Male";
                binding.supplierFemale.setEnabled(false);
            } else {
                SupplierSex = null;
                binding.supplierFemale.setEnabled(true);
            }
        });
        binding.supplierFemale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isFemale == binding.supplierFemale.isChecked()) {
                SupplierSex = "Female";
                binding.supplierMale.setEnabled(false);
            } else {
                SupplierSex = null;
                binding.supplierMale.setEnabled(true);
            }
        });
    }

    private void OnSaveSupplier(View view) {
        OnCheckSupplierSex();
        SupplierName = String.valueOf(binding.txtSupplierName.getText());
        SupplierPhone = String.valueOf(binding.txtSupplierPhone.getText());
        SupplierAddress = String.valueOf(binding.txtSupplierAddress.getText());
        supplier = new Supplier(SupplierName, SupplierSex, SupplierPhone, SupplierAddress, SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate());
        if (SupplierName != null) {
            new Thread(() -> {
                viewModel.createSupplier(supplier);
                handler.post(this::OnUpdateUI);
            }).start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Supplier, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnShowAllSupplier() {
        viewModel.getAllSupplier().observe(getViewLifecycleOwner(), suppliers -> {
            if (suppliers.size() != 0) {
                binding.txtNoSupplierFound.setVisibility(View.GONE);
                binding.listShowSupplier.setVisibility(View.VISIBLE);
                binding.listShowSupplier.setAdapter(new AdapterSupplier(suppliers, requireActivity()));
            }
            binding.listShowSupplier.setOnItemClickListener((adapterView, view, i, l) -> {
                binding.txtSupplierPhone.setText(suppliers.get(i).getSupplierPhoneNumber());
                binding.txtSupplierName.setText(suppliers.get(i).getSupplierName());
                binding.txtSupplierAddress.setText(suppliers.get(i).getSupplierAddress());
                supplierId = suppliers.get(i).getSupplierId();
                OnShowDeleteUpdate();
                String sex = suppliers.get(i).getSupplierSex();
                if (sex.contains("Male")) {
                    binding.supplierMale.setChecked(true);
                    binding.supplierFemale.setChecked(false);
                } else if (sex.contains("Female")) {
                    binding.supplierMale.setChecked(false);
                    binding.supplierFemale.setChecked(true);
                }
                OnShowAddSupplier();
            });
        });
    }

    private void OnUpdateUI() {
        binding.layoutAddSupplier.setVisibility(View.GONE);
        binding.listShowSupplier.setVisibility(View.VISIBLE);
        binding.btnDeleteSupplier.setVisibility(View.GONE);
        binding.btnUpdateSupplier.setVisibility(View.GONE);
        binding.btnSaveSupplier.setVisibility(View.VISIBLE);
        binding.supplierMale.setChecked(false);
        binding.supplierFemale.setChecked(false);
        binding.txtSupplierName.setText(null);
        binding.txtSupplierPhone.setText(null);
        binding.txtSupplierAddress.setText(null);
    }

    private void OnShowAddSupplier() {
        binding.layoutAddSupplier.setVisibility(View.VISIBLE);
        binding.listShowSupplier.setVisibility(View.GONE);
    }
}