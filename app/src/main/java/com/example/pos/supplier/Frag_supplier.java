package com.example.pos.supplier;

import android.os.Bundle;
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

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragSupplierBinding;

public class Frag_supplier extends Fragment {
    FragmentFragSupplierBinding binding;
    Transition transition;

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
        transition = new Slide();
        transition.setDuration(500);

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_supplier) {
            TransitionManager.beginDelayedTransition(binding.layoutAddSupplier, transition);
            TransitionManager.beginDelayedTransition(binding.listShowSupplier, transition);
            binding.layoutAddSupplier.setVisibility(binding.layoutAddSupplier.getVisibility() ==
                    View.GONE ? View.VISIBLE : View.GONE);
            binding.listShowSupplier.setVisibility(binding.listShowSupplier.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_supplier).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}