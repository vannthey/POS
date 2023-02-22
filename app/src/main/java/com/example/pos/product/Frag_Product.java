package com.example.pos.product;

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

import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragProductBinding;

import java.util.List;

public class Frag_Product extends Fragment {
    FragmentFragProductBinding binding;
    List<Product> itemList;
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
        binding = FragmentFragProductBinding.inflate(getLayoutInflater(), container, false);
        binding.btnSaveProduct.setOnClickListener(this::onSaveProduct);
        binding.btnCancelAddProduct.setOnClickListener(this::onCancelSaveProduct);
        transition = new Slide();
        onShowAllProduct();
        return binding.getRoot();
    }

    private void onCancelSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
    }

    private void onSaveProduct(View view) {
        binding.layoutAddProduct.setVisibility(View.GONE);
        binding.layoutShowProduct.setVisibility(View.VISIBLE);
        onShowAllProduct();
    }

    private void onShowAllProduct() {

        new Thread(() -> {
            itemList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
        }).start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_product) {
            TransitionManager.beginDelayedTransition(binding.layoutAddProduct, transition);
            binding.layoutAddProduct.setVisibility(binding.layoutAddProduct.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            binding.layoutShowProduct.setVisibility(binding.layoutShowProduct.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_product).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}