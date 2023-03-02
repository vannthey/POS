package com.example.pos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pos.account.ManageAccountActivity;
import com.example.pos.category.Frag_category;
import com.example.pos.customer.Frag_customer;
import com.example.pos.dasboard.Frag_Dashboard;
import com.example.pos.databinding.ActivityMainBinding;
import com.example.pos.inventory.Frag_inventory;
import com.example.pos.product.Frag_Product;
import com.example.pos.report.Frag_report;
import com.example.pos.sale.Frag_sale;
import com.example.pos.setting.Preference;
import com.example.pos.supplier.Frag_supplier;
import com.example.pos.unit.Frag_unit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBarDrawerToggle drawerToggle;
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences sharedPreferences, sharedDefault;
    SharedPreferences.Editor editor, editorDefault;
    private final String SaveUserLogin = "UserLogin";
    private final String SaveUserFullName = "SaveUserFullName";
    private final String DefaultUsername = "Admin";
    private final String DefaultUser = "DefaultUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.actionBar.customActionbar);
        setTitle("Dashboard");
        sharedDefault = getSharedPreferences(DefaultUser, MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(SaveUserLogin, MODE_PRIVATE);
        String DefaultUserLogin = sharedDefault.getString(DefaultUsername, "");
        if (DefaultUserLogin.contains("Admin")) {
            binding.navDrawerView.getMenu().findItem(R.id.item).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.category).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.inventory).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.dashboad).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.sale).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.setting).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.report).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.payment).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.customer).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.supplier).setVisible(false);
            binding.navDrawerView.getMenu().findItem(R.id.unit).setVisible(false);
            TextView userOnNavDrawer =
                    binding.navDrawerView.getHeaderView(0).findViewById(R.id.userOnNavDrawer);
            userOnNavDrawer.setText("Admin");
            TextView userRoleOnNavDrawer =
                    binding.navDrawerView.getHeaderView(0).findViewById(R.id.userRoleOnNavDrawer);
            userRoleOnNavDrawer.setText("Default User");
            binding.mainFrameLayout.setVisibility(View.GONE);
        } else {
            onSetUserNameAndRoleOnNavDrawer();
        }

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

    }

    private void onSetUserNameAndRoleOnNavDrawer() {
        ImageView userProfileOnNavDrawer =
                binding.navDrawerView.getHeaderView(0).findViewById(R.id.image_profile_user);
        String path = SharedPreferenceHelper.getInstance().getSaveUserProfilePath(this);
        Glide.with(this).load(path).into(userProfileOnNavDrawer);
        TextView userOnNavDrawer =
                binding.navDrawerView.getHeaderView(0).findViewById(R.id.userOnNavDrawer);
        userOnNavDrawer.setText(SharedPreferenceHelper.getInstance().getSaveUserLoginName(this));
        TextView userRoleOnNavDrawer =
                binding.navDrawerView.getHeaderView(0).findViewById(R.id.userRoleOnNavDrawer);
        userRoleOnNavDrawer.setText(SharedPreferenceHelper.getInstance().getSaveUserLoginRole(this));
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
                setTitle(R.string.sale);
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
            case R.id.supplier:
                setTitle(R.string.supplier);
                setStateFragment(new Frag_supplier());
                break;
            case R.id.customer:
                setTitle(R.string.customer);
                setStateFragment(new Frag_customer());
                break;
            case R.id.unit:
                setTitle(R.string.unit);
                setStateFragment(new Frag_unit());
                break;
            case R.id.logout:
                editor = sharedPreferences.edit();
                editorDefault = sharedDefault.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("ARE YOU SURE WANT TO LOG OUT?");
                builder.setNegativeButton("NO", (dialogInterface, i) ->
                        dialogInterface.dismiss()
                );
                builder.setPositiveButton("YES", (dialogInterface, i) -> {
                    editorDefault.clear();
                    editorDefault.commit();
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

    public void SaleNavigator() {
        View view = binding.navDrawerView.findViewById(R.id.sale);
        view.callOnClick();
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