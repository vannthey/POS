package com.example.pos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.pos.dasboard.Frag_Dashboard;
import com.example.pos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.actionBar.customActionbar);

        drawerToggle = new ActionBarDrawerToggle(this,
                binding.navDrawerLayout,
                binding.actionBar.customActionbar,
                R.string.Navigation_drawer_open,
                R.string.Navigation_drawer_close);

        binding.navDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        binding.navDrawerView.setNavigationItemSelectedListener(this::NavigationSelected);

    }

    private boolean NavigationSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dashboad:
                setStateFragement(new Frag_Dashboard());
                break;
            case R.id.sale:
                break;
            case R.id.category:
                break;
            case R.id.inventory:
                break;
            case R.id.report:
                break;
            case R.id.setting:
                break;
            case R.id.logout:
                break;

        }
        binding.navDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setStateFragement(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragment).commit();
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