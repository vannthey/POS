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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragUnitBinding.inflate(inflater, container, false);
        handler = new Handler();
        binding.btnSaveUnit.setOnClickListener(this::OnSaveUnit);

        OnShowAllUnit();

        return binding.getRoot();
    }

    private void OnSaveUnit(View view) {
        String uniTitle = String.valueOf(binding.unitTitle.getText());
        String QtyStr = String.valueOf(binding.unitQty.getText());
        int unitQty = Integer.parseInt(QtyStr);
        new Thread(() -> {
            POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().createUnit
                    (new Unit(uniTitle, unitQty,
                            SharedPreferenceHelper.getInstance().getSaveUserLoginName(requireContext()), CurrentDateHelper.getCurrentDate()));
            handler.post(()->{
                OnShowAllUnit();
                OnShowStateChangeView();
            });
        }).start();

    }

    private void OnShowAllUnit() {
        new Thread(() -> {
            unitList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllUnit();
            handler.post(() -> binding.listUnit.setAdapter(new AdapterUnit(unitList, requireContext())));
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_unit) {
            OnHideStateChangeView();
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_unit).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}