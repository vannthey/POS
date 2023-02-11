package com.example.pos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.pos.category.Frag_category;
import com.example.pos.dasboard.Frag_Dashboard;
import com.example.pos.databinding.ActivityMainBinding;
import com.example.pos.inventory.Frag_inventory;
import com.example.pos.report.Frag_report;
import com.example.pos.sale.Frag_sale;
import com.example.pos.setting.Preference;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle drawerToggle;

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
                R.string.Navigation_drawer_close) {
        };

        /*
        Set state to navigation drawer to it visible on action bar
         */
        binding.navDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.navDrawerView.setNavigationItemSelectedListener(this::NavigationSelected);

        binding.navDrawerView.setCheckedItem(R.id.dashboad);
        setStateFragment(new Frag_Dashboard());

    }

    private void onHideItemNavigationDrawer() {
       binding.navDrawerView.getMenu().getItem(R.id.category);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_items_cart) {
            setStateFragment(new Frag_sale());
            binding.navDrawerView.setCheckedItem(R.id.sale);
        }else if (item.getItemId() == R.id.add_category){
            Toast.makeText(this, "adding category........", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /*
    select state in nav drawer to set fragment to main activity
     */
    @SuppressLint("NonConstantResourceId")
    private boolean NavigationSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dashboad:
                setTitle("Dashboard");
                setStateFragment(new Frag_Dashboard());
                break;
            case R.id.sale:
                setTitle("Sale");
                setStateFragment(new Frag_sale());
                break;
            case R.id.category:
                setTitle("Category");
                setStateFragment(new Frag_category());
                break;
            case R.id.inventory:
                setTitle("Inventory");
                setStateFragment(new Frag_inventory());
                break;
            case R.id.report:
                setTitle("Report");
                setStateFragment(new Frag_report());
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("ARE YOU SURE WANT TO LOG OUT?");
                builder.setNegativeButton("NO", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                builder.setPositiveButton("YES", (dialogInterface, i) -> {
                    finish();
                    System.exit(0);
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }
}