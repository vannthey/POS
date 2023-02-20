package com.example.pos.setting;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pos.R;
import com.example.pos.databinding.ActivityPreferenceBinding;

import java.util.Locale;

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
        setContentView(binding.getRoot());

        binding.changeCompanyAddress.setOnClickListener(v -> Toast.makeText(this, "Company Address", Toast.LENGTH_SHORT).show());
        binding.changeCompanyName.setOnClickListener(v -> Toast.makeText(this, "Company Name", Toast.LENGTH_SHORT).show());
        binding.changeAppLanguage.setOnClickListener(v -> {
            binding.layoutPreference.setVisibility(View.GONE);
            binding.layoutFramePreference.setVisibility(View.VISIBLE);
            OnChangeLanguage(new Frag_language());
            Toast.makeText(this, "Language", Toast.LENGTH_SHORT).show();
        });
        binding.changeCurrencyExchange.setOnClickListener(v -> Toast.makeText(this, "Currency Exchange", Toast.LENGTH_SHORT).show());
        binding.changeCompanyPhoneNumber.setOnClickListener(v -> Toast.makeText(this, "Phone Number", Toast.LENGTH_SHORT).show());
        binding.changeCompanyLogo.setOnClickListener(v -> Toast.makeText(this, "Company Logo", Toast.LENGTH_SHORT).show());
        binding.changePaymentMethod.setOnClickListener(v -> Toast.makeText(this, "Payment Method", Toast.LENGTH_SHORT).show());
        binding.editPrintInvoice.setOnClickListener(v -> Toast.makeText(this, "Print Invoice", Toast.LENGTH_SHORT).show());

    }

    private void OnChangeLanguage(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame_preference,fragment).commit();
    }

}