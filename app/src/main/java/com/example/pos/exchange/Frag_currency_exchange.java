package com.example.pos.exchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.pos.databinding.FragmentFragCurrencyExchangeBinding;

public class Frag_currency_exchange extends Fragment {
    FragmentFragCurrencyExchangeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCurrencyExchangeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}