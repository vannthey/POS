package com.example.pos.setting;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.databinding.ActivityPreferenceBinding;

public class Preference extends AppCompatActivity {

    ActivityPreferenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreferenceBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.customActionbarSetting.customActionbar);
        setTitle("Setting");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.customActionbarSetting.customActionbar.setNavigationOnClickListener(v -> this.finish());

        binding.changeCompanyAddress.setOnClickListener(v -> Toast.makeText(this, "Company Address", Toast.LENGTH_SHORT).show());
        binding.changeCompanyName.setOnClickListener(v -> Toast.makeText(this, "Company Name", Toast.LENGTH_SHORT).show());
        binding.changeAppLanguage.setOnClickListener(v -> Toast.makeText(this, "Language", Toast.LENGTH_SHORT).show());
        binding.changeCurrencyExchange.setOnClickListener(v -> Toast.makeText(this, "Currency Exchange", Toast.LENGTH_SHORT).show());
        binding.changeCompanyPhoneNumber.setOnClickListener(v -> Toast.makeText(this, "Phone Number", Toast.LENGTH_SHORT).show());
        binding.changeCompanyLogo.setOnClickListener(v -> Toast.makeText(this, "Company Logo", Toast.LENGTH_SHORT).show());
        binding.changePaymentMethod.setOnClickListener(v -> Toast.makeText(this, "Payment Method", Toast.LENGTH_SHORT).show());
        binding.editPrintInvoice.setOnClickListener(v -> Toast.makeText(this, "Print Invoice", Toast.LENGTH_SHORT).show());


        setContentView(binding.getRoot());
    }

}