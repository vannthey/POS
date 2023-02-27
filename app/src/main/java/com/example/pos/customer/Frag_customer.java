package com.example.pos.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pos.databinding.FragmentFragCustomerBinding;

public class Frag_customer extends Fragment {
    FragmentFragCustomerBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCustomerBinding.inflate(getLayoutInflater(), container, false);

        binding.btnPickDate.setOnClickListener(this::ShowDatePicker);

        return binding.getRoot();
    }

    private void ShowDatePicker(View view) {
        //   MaterialDatePicker.Builder.datePicker().setTitleText("Select").build().show
        //   (requireActivity().getSupportFragmentManager(), "");
    }
}