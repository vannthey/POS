package com.example.pos.supplier;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragSupplierBinding;

import java.util.List;

public class Frag_supplier extends Fragment {
    FragmentFragSupplierBinding binding;
    Transition transitionAdd;
    Transition transitionList;

    List<Supplier> supplierList;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSupplierBinding.inflate(getLayoutInflater(), container, false);
        handler = new Handler();
        binding.btnCancelSupplier.setOnClickListener(v -> OnClearDataSupplier());
        binding.btnSaveSupplier.setOnClickListener(this::OnSaveSupplier);
        OnCreateMenu();
        OnGetAllSupplier();
        OnCheckSupplierSex();
        OnSupplierItemClick();

        return binding.getRoot();
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
                    OnShowAddSupplier();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void OnSupplierItemClick() {
        binding.listShowSupplier.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.txtSupplierPhone.setText(supplierList.get(i).getSupplierPhoneNumber());
            binding.txtSupplierName.setText(supplierList.get(i).getSupplierName());
            binding.txtSupplierAddress.setText(supplierList.get(i).getSupplierAddress());
            supplierId = supplierList.get(i).getSupplierId();
            String sex = supplierList.get(i).getSupplierSex();
            if (sex.contains("Male")) {
                binding.supplierMale.setChecked(true);
                binding.supplierFemale.setChecked(false);
            } else if (sex.contains("Female")) {
                binding.supplierMale.setChecked(false);
                binding.supplierFemale.setChecked(true);
            }
            OnShowDeleteUpdateBtn();
            OnShowAddSupplier();
        });
    }

    private void OnShowDeleteUpdateBtn() {
        binding.btnDeleteSupplier.setVisibility(View.VISIBLE);
        binding.btnUpdateSupplier.setVisibility(View.VISIBLE);
        binding.btnSaveSupplier.setVisibility(View.GONE);
        binding.btnUpdateSupplier.setOnClickListener(v -> {
            SupplierName = String.valueOf(binding.txtSupplierName.getText());
            SupplierPhone = String.valueOf(binding.txtSupplierPhone.getText());
            SupplierAddress = String.valueOf(binding.txtSupplierAddress.getText());
            new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().updateSupplierById(SupplierName, SupplierAddress, SupplierSex, SupplierPhone, supplierId);
                handler.post(() -> {
                    OnClearDataSupplier();
                    OnHideDeleteUpdate();
                });
            }).start();
        });
        binding.btnDeleteSupplier.setOnClickListener(v -> new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteSupplierById(supplierId);
            handler.post(() -> {
                OnHideDeleteUpdate();
                OnClearDataSupplier();
            });
        }).start());
    }

    private void OnHideDeleteUpdate() {
        binding.btnDeleteSupplier.setVisibility(View.GONE);
        binding.btnUpdateSupplier.setVisibility(View.GONE);
        binding.btnSaveSupplier.setVisibility(View.VISIBLE);
    }

    private void OnClearDataSupplier() {
        binding.supplierMale.setChecked(false);
        binding.supplierFemale.setChecked(false);
        binding.txtSupplierName.setText(null);
        binding.txtSupplierPhone.setText(null);
        binding.txtSupplierAddress.setText(null);
        OnHideDeleteUpdate();
        OnHideAddSupplier();
    }

    private void OnHideAddSupplier() {
        binding.layoutAddSupplier.setVisibility(View.GONE);
        binding.listShowSupplier.setVisibility(View.VISIBLE);

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
        supplier = new Supplier(SupplierName, SupplierSex, SupplierPhone, SupplierAddress,
                SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()),
                CurrentDateHelper.getCurrentDate());
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createSupplier(supplier);
            handler.post(() -> {
                OnGetAllSupplier();
                OnClearDataSupplier();
            });
        }).start();
    }

    private void OnGetAllSupplier() {
        new Thread(() -> {
            supplierList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSupplier();
            handler.post(() -> {
                if (supplierList.size() != 0) {
                    binding.txtNoSupplierFound.setVisibility(View.GONE);
                    binding.listShowSupplier.setVisibility(View.VISIBLE);
                    binding.listShowSupplier.setAdapter(new AdapterSupplier(supplierList,
                            requireContext()));
                }
            });
        }).start();

    }


    private void OnShowAddSupplier() {
        binding.layoutAddSupplier.setVisibility(View.VISIBLE);
        binding.listShowSupplier.setVisibility(View.GONE);
    }
}