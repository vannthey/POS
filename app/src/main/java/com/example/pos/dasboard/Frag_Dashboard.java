package com.example.pos.dasboard;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pos.Database.Entity.Product;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragDashboardBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class Frag_Dashboard extends Fragment {
    List<Product> productList;
    Handler handler;
    FragmentFragDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragDashboardBinding.inflate(inflater, container, false);
        handler = new Handler();
        new Thread(() -> {
            productList =
                    POSDatabase.getInstance(requireContext().getApplicationContext()).getDao().getAllProduct();
            handler.post(() -> binding.gridDashboard.setAdapter(new AdapterProductDashboard(productList, requireContext())));
        }).start();


        /*
        Select QR icon in edite text // 11/2/2023
         */

//        binding.searchViewDashboard.setOnTouchListener((view, motionEvent) -> {
//            final int DRAWABLE_RIGHT = 0;
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                if (motionEvent.getRawX() >= (binding.searchViewDashboard.getRight() - binding.searchViewDashboard.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//
//                    launchQRScan();
//
//                }
//            }
//            return false;
//        });
//        binding.tabLayoutOnDashboard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        return binding.getRoot();
    }

    private void launchQRScan() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        options.setPrompt("Scanning Coding");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setBarcodeImageEnabled(true);
        barcodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(requireContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        menu.findItem(R.id.app_language).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }
}