package com.example.pos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.pos.account.ManageAccountActivity;
import com.example.pos.category.Frag_category;
import com.example.pos.dasboard.Frag_Dashboard;
import com.example.pos.databinding.ActivityMainBinding;
import com.example.pos.inventory.Frag_inventory;
import com.example.pos.product.Frag_Product;
import com.example.pos.report.Frag_report;
import com.example.pos.sale.Frag_sale;
import com.example.pos.setting.Preference;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle drawerToggle;
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUserFullName = "SaveUserFullName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.actionBar.customActionbar);
        setTitle("Dashboard");
        drawerToggle = new ActionBarDrawerToggle(this,
                binding.navDrawerLayout,
                binding.actionBar.customActionbar,
                R.string.Navigation_drawer_open,
                R.string.Navigation_drawer_close);

        /*
        Set state to navigation drawer to it visible on action bar
         */
        binding.navDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        binding.navDrawerView.setNavigationItemSelectedListener(this::NavigationSelected);
        binding.navDrawerView.setCheckedItem(R.id.dashboad);
        setStateFragment(new Frag_Dashboard());
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);


    }

    private void onSetUserNameAndRoleOnNavDrawer() {
        String fullName = sharedPreferences.getString(SaveUserFullName, "");
        TextView userOnNavDrawer =
                binding.navDrawerView.getHeaderView(0).findViewById(R.id.userOnNavDrawer);
                userOnNavDrawer.setText(fullName);
        TextView userRoleOnNavDrawer =
                binding.navDrawerView.getHeaderView(0).findViewById(R.id.userRoleOnNavDrawer);
    }

    /*
    select state in nav drawer to set fragment to main activity
     */
    @SuppressLint("NonConstantResourceId")
    private boolean NavigationSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item:
                setTitle(R.string.product);
                setStateFragment(new Frag_Product());
                break;
            case R.id.dashboad:
                setTitle(R.string.dashboard);
                setStateFragment(new Frag_Dashboard());
                break;
            case R.id.sale:
                setTitle(R.string.dashboard);
                setStateFragment(new Frag_sale());
                break;
            case R.id.category:
                setTitle(R.string.category);
                setStateFragment(new Frag_category());
                break;
            case R.id.inventory:
                setTitle(R.string.inventory);
                setStateFragment(new Frag_inventory());
                break;
            case R.id.report:
                setTitle(R.string.report);
                setStateFragment(new Frag_report());
                break;
            case R.id.logout:
                editor = sharedPreferences.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("ARE YOU SURE WANT TO LOG OUT?");
                builder.setNegativeButton("NO", (dialogInterface, i) ->
                        dialogInterface.dismiss()
                );
                builder.setPositiveButton("YES", (dialogInterface, i) -> {
                    editor.clear();
                    editor.commit();
                    finish();
                    System.exit(0);
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.manage_account:
                startActivity(new Intent(this, ManageAccountActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(this, Preference.class));
                break;
        }
        binding.navDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setStateFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.main_frame_layout, fragment).
                commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.navDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.navDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            return;
        }
//        else {
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//            }
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Click Again", Toast.LENGTH_SHORT).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
    }
}