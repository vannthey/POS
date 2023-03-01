package com.example.pos.unit;

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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragUnitBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnSaveUnit.setOnClickListener(this::OnSaveUnit);
        OnShowAllUnit();
        OnCreateMenu();
        return binding.getRoot();
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
                    OnHideStateChangeView();
                }
                return true;
            }
        },getViewLifecycleOwner());
    }

    private void OnSaveUnit(View view) {
        String uniTitle = String.valueOf(binding.unitTitle.getText());
        String QtyStr = String.valueOf(binding.unitQty.getText());
        int unitQty = Integer.parseInt(QtyStr);
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createUnit
                    (new Unit(uniTitle, unitQty,
                            SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate()));
            handler.post(() -> {
                OnShowAllUnit();
                OnShowStateChangeView();
            });
        }).start();

    }

    private void OnShowAllUnit() {
        new Thread(() -> {
            unitList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllUnit();
            handler.post(() -> {
                if (unitList.size() != 0) {
                    binding.txtNoUnitFound.setVisibility(View.GONE);
                    binding.listUnit.setVisibility(View.VISIBLE);
                    binding.listUnit.setAdapter(new AdapterUnit(unitList, requireContext()));
                }
            });
        }).start();
    }


    private void OnHideStateChangeView() {
        binding.listUnit.setVisibility(View.GONE);
        binding.layoutAddUnit.setVisibility(View.VISIBLE);
        binding.unitQty.setText(null);
        binding.unitTitle.setText(null);
    }

    private void OnShowStateChangeView() {
        binding.listUnit.setVisibility(View.VISIBLE);
        binding.layoutAddUnit.setVisibility(View.GONE);
        binding.unitQty.setText(null);
        binding.unitTitle.setText(null);
    }
}