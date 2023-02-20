package com.example.pos.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.R;
import com.example.pos.databinding.FragmentFragCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class Frag_category extends Fragment {
    FragmentFragCategoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCategoryBinding.inflate(inflater, container, false);

        List<CategoryModel> item_category = new ArrayList<>();
        binding.gridCategory.setAdapter(new CategoryAdapter(item_category, requireContext()));

        binding.btnCancelCategory.setOnClickListener(this::onCancelSaveCategory);

        return binding.getRoot();
    }

    private void onCancelSaveCategory(View view) {
        binding.layoutAddCategory.setVisibility(View.GONE);
        binding.gridCategory.setVisibility(View.VISIBLE);
        getParentFragmentManager().beginTransaction().detach(Frag_category.this).attach(Frag_category.this).commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_category:
                binding.layoutAddCategory.setVisibility(View.VISIBLE);
                binding.gridCategory.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Adding Category", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.add_category).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}