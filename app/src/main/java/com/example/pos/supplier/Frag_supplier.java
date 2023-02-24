package com.example.pos.supplier;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.pos.Database.Entity.Supplier;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragSupplierBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Frag_supplier extends Fragment {
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUserRole = "SaveUserRole";
    FragmentFragSupplierBinding binding;
    Transition transitionAdd;
    Transition transitionList;
    SharedPreferences preferences;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragSupplierBinding.inflate(getLayoutInflater(), container, false);
        transitionAdd = new Slide(Gravity.END);
        transitionList = new Slide(Gravity.START);
        transitionAdd.setDuration(500);
        transitionList.setDuration(500);
        binding.btnCancelSupplier.setOnClickListener(this::OnCancelSupplier);
        binding.btnSaveSupplier.setOnClickListener(this::OnSaveSupplier);

        OnGetAllSupplier();
        OnCheckSupplierSex();
        OnSupplierItemClick();

        return binding.getRoot();
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
            OnShowListAtSupplier();
        });
    }

    private void OnShowDeleteUpdateBtn() {
        binding.btnDeleteSupplier.setVisibility(View.VISIBLE);
        binding.btnUpdateSupplier.setVisibility(View.VISIBLE);
        binding.btnSaveSupplier.setVisibility(View.GONE);
        binding.btnUpdateSupplier.setOnClickListener(v->{
            SupplierName = String.valueOf(binding.txtSupplierName.getText());
            SupplierPhone = String.valueOf(binding.txtSupplierPhone.getText());
            SupplierAddress = String.valueOf(binding.txtSupplierAddress.getText());
            new Thread(()->{
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().updateSupplierById(SupplierName,SupplierAddress,SupplierSex,SupplierPhone,supplierId);
            }).start();
        });
        binding.btnDeleteSupplier.setOnClickListener(v->{
            new Thread(()->{
               POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteSupplierById(supplierId);
            }).start();
        });
    }

    private void OnCancelSupplier(View view) {
        binding.supplierMale.setChecked(false);
        binding.supplierFemale.setChecked(false);
        OnHideListAtSupplier();
    }

    private void OnHideListAtSupplier() {
        TransitionManager.beginDelayedTransition(binding.layoutAddSupplier, transitionAdd);
        TransitionManager.beginDelayedTransition(binding.listShowSupplier, transitionList);
        binding.layoutAddSupplier.setVisibility(binding.layoutAddSupplier.getVisibility() ==
                View.VISIBLE ? View.GONE : View.VISIBLE);
        binding.listShowSupplier.setVisibility(binding.listShowSupplier.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

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
        preferences = requireContext().getSharedPreferences(SaveUserLogin, 0);
        String UserRole = preferences.getString(SaveUserRole, "");
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
        String createDate = simpleDateFormat.format(current);
        supplier = new Supplier(SupplierName, SupplierSex, SupplierPhone, SupplierAddress, UserRole, createDate);
        handler = new Handler();
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createSupplier(supplier);
            handler.post(() -> {
                OnGetAllSupplier();
                OnCancelSupplier(view);
                binding.txtSupplierName.setText(null);
                binding.txtSupplierAddress.setText(null);
                binding.txtSupplierPhone.setText(null);
            });
        }).start();
    }

    private void OnGetAllSupplier() {
        handler = new Handler();
        new Thread(() -> {
            supplierList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllSupplier();
            handler.post(() -> {
                binding.listShowSupplier.setAdapter(new AdapterSupplier(supplierList, requireContext()));
            });
        }).start();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_supplier) {
            OnShowListAtSupplier();
        }
        return super.onOptionsItemSelected(item);
    }

    private void OnShowListAtSupplier() {
        TransitionManager.beginDelayedTransition(binding.layoutAddSupplier, transitionAdd);
        TransitionManager.beginDelayedTransition(binding.listShowSupplier, transitionList);
        binding.layoutAddSupplier.setVisibility(binding.layoutAddSupplier.getVisibility() ==
                View.GONE ? View.VISIBLE : View.GONE);
        binding.listShowSupplier.setVisibility(binding.listShowSupplier.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_supplier).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}