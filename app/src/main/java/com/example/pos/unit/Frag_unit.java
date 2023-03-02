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

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.Unit;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.SharedPreferenceHelper;
import com.example.pos.databinding.FragmentFragUnitBinding;

import java.util.List;

public class Frag_unit extends Fragment {
    FragmentFragUnitBinding binding;
    List<Unit> unitList;
    Handler handler;
    String uniTitle;
    int unitQty;
    int unitId;
    Thread thread;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragUnitBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnSaveUnit.setOnClickListener(this::OnSaveUnit);
        binding.btnCancelUnit.setOnClickListener(this::OnCancelUnit);
        binding.btnDeleteUnit.setOnClickListener(this::OnDeleteUnit);
        binding.btnUpdateUnit.setOnClickListener(this::OnUpdateUnit);
        OnShowAllUnit();
        OnCreateMenu();
        return binding.getRoot();
    }

    private void OnUpdateUnit(View view) {
        onGetData();
        if (binding.unitTitle.getText() != null) {
            thread = new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().updateUnitById(uniTitle, unitQty, unitId);
                handler.post(() -> {
                    OnHideAddUnit();
                    OnShowAllUnit();
                    OnHideDeleteUpdate();
                });
            });
            thread.start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnDeleteUnit(View view) {
        thread = new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().deleteUnitById(unitId);
            handler.post(() -> {
                OnShowAllUnit();
                OnHideAddUnit();
                OnHideDeleteUpdate();
            });
        });
        thread.start();
    }

    private void OnCancelUnit(View view) {
        OnHideDeleteUpdate();
        OnHideAddUnit();
    }

    @Override
    public void onDetach() {
        thread.interrupt();
        super.onDetach();
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
                    binding.unitQty.setText(null);
                    OnShowAddUnit();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void onSetData(String title, int Qty) {
        binding.unitTitle.setText(title);
        binding.unitQty.setText(String.valueOf(Qty));
    }

    private void onGetData() {
        uniTitle = String.valueOf(binding.unitTitle.getText());
        unitQty = Integer.parseInt(String.valueOf(binding.unitQty.getText()));
    }

    private void OnSaveUnit(View view) {
        onGetData();
        if (binding.unitTitle.getText() != null) {
            thread = new Thread(() -> {
                POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createUnit
                        (new Unit(uniTitle, unitQty,
                                SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate()));
                handler.post(() -> {
                    OnShowAllUnit();
                    OnHideAddUnit();
                });
            });
            thread.start();
        } else {
            Toast.makeText(requireContext(), R.string.Please_Input_Unit_Name, Toast.LENGTH_SHORT).show();
        }
    }

    private void OnShowAllUnit() {
        thread = new Thread(() -> {
            unitList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllUnit();
            handler.post(() -> {
                if (unitList.size() != 0) {
                    binding.txtNoUnitFound.setVisibility(View.GONE);
                    binding.listUnit.setVisibility(View.VISIBLE);
                    binding.listUnit.setAdapter(new AdapterUnit(unitList, requireContext()));
                }
            });
            onClickListUnit();
        });
        thread.start();
    }

    private void onClickListUnit() {
        binding.listUnit.setOnItemClickListener((adapterView, view, i, l) -> {
            unitId = unitList.get(i).getUnitId();
            onSetData(unitList.get(i).unitTitle, unitList.get(i).getUnitQty());
            OnShowAddUnit();
            OnShowDeleteUpdate();
        });
    }

    private void OnHideDeleteUpdate() {
        binding.btnSaveUnit.setVisibility(View.VISIBLE);
        binding.btnUpdateUnit.setVisibility(View.GONE);
        binding.btnDeleteUnit.setVisibility(View.GONE);
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

    private void OnHideAddUnit() {
        binding.listUnit.setVisibility(View.VISIBLE);
        binding.layoutAddUnit.setVisibility(View.GONE);
    }
}