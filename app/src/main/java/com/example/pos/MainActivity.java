package com.example.pos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.pos.category.Frag_category;
import com.example.pos.dasboard.Frag_Dashboard;
import com.example.pos.databinding.ActivityMainBinding;
import com.example.pos.inventory.Frag_inventory;
import com.example.pos.report.Frag_report;
import com.example.pos.sale.Frag_sale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.actionBar.customActionbar);
        setTitle("Blue Technology");

        drawerToggle = new ActionBarDrawerToggle(this,
                binding.navDrawerLayout,
                binding.actionBar.customActionbar,
                R.string.Navigation_drawer_open,
                R.string.Navigation_drawer_close);

        binding.navDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        binding.navDrawerView.setNavigationItemSelectedListener(this::NavigationSelected);

        setStateFragement(new Frag_Dashboard());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Toast.makeText(this, "Shopping Cart", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private boolean NavigationSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dashboad:
                setStateFragement(new Frag_Dashboard());
                break;
            case R.id.sale:
                setStateFragement(new Frag_sale());
                break;
            case R.id.category:
                setStateFragement(new Frag_category());
                break;
            case R.id.inventory:
                setStateFragement(new Frag_inventory());
                break;
            case R.id.report:
                setStateFragement(new Frag_report());
                break;
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;

        }
        binding.navDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setStateFragement(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
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
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }
}