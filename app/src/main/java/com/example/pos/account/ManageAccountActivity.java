package com.example.pos.account;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.ActivityManageAccountBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ManageAccountActivity extends AppCompatActivity {

    private static final String TAG = "User Role";
    ActivityManageAccountBinding binding;
    Transition transition;
    String UserRole;

    /*
    Position
     */
    Boolean isAdmin = true,
            isSeller = true,
            isCashier = true,
            isManager = true;
    /*
    permission
     */
    Boolean canDiscount = false,
            canUpdateItem = false,
            canAddItem = false,
            canAddCategory = false,
            canDeleteItem = false,
            AllowAllPermission;
    UserAccount userAccount;
    List<UserAccount> userAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAccountBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.customActionbarManageAccount.customActionbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.customActionbarManageAccount.customActionbar.setNavigationOnClickListener(v -> finish());
        setTitle("Account Management");
        setContentView(binding.getRoot());
        OnAnimationChangeLayout();
        binding.btnSaveCreateUser.setOnClickListener(this::OnSaveCreateUser);
        binding.btnCancelCreateUser.setOnClickListener(this::OnCancelCreateUser);
        OnStateCheckBoxUserRole();
        OnCallAllUserFromDB();
    }

    private void OnAnimationChangeLayout() {
        TransitionManager.beginDelayedTransition(binding.layoutAddUser, transition);
        transition = new Slide();
        transition.setDuration(6000);
    }

    private void OnCancelCreateUser(View view) {
        binding.showListAllUser.setVisibility(View.VISIBLE);
        binding.layoutAddUser.setVisibility(View.GONE);
    }

    private void OnCallAllUserFromDB() {
        Handler handler = new Handler();
        new Thread(() -> {
            userAccountList =
                    POSDatabase.getInstance(getApplicationContext()).getDao().userAccount();
            handler.post(() -> binding.showListAllUser.setAdapter(new AdapterAccountManager(userAccountList, this)));
        }).start();
    }

    private void OnSaveCreateUser(View view) {
        Handler handler = new Handler();
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = simpleFormatter.format(current);
        String Firstname = binding.addFirstName.getText().toString();
        String Lastname = binding.addLastName.getText().toString();
        String Username = binding.addUserName.getText().toString();
        String Password = binding.addUserPassword.getText().toString();
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().createUser(userAccount);
            handler.post(() -> {
                binding.layoutAddUser.setVisibility(View.GONE);
                binding.showListAllUser.setVisibility(View.VISIBLE);
                OnCallAllUserFromDB();
            });
        }).start();
    }

    private void OnStateCheckBoxUserRole() {
        binding.isAdmin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isAdmin == binding.isAdmin.isChecked()) {
                binding.isManager.setEnabled(false);
                binding.isSeller.setEnabled(false);
                binding.isCashier.setEnabled(false);
                isManager = false;
                isCashier = false;
                isSeller = false;
                UserRole = "Admin";
            } else {
                UserRole = null;
                isManager = true;
                isCashier = true;
                isSeller = true;
                binding.isManager.setEnabled(true);
                binding.isSeller.setEnabled(true);
                binding.isCashier.setEnabled(true);
            }
        });
        binding.isManager.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isManager == binding.isManager.isChecked()) {
                UserRole = "Manager";
                binding.isAdmin.setEnabled(false);
                binding.isSeller.setEnabled(false);
                binding.isCashier.setEnabled(false);
                isSeller = false;
                isAdmin = false;
                isCashier = false;
            } else {
                UserRole = null;
                isSeller = true;
                isAdmin = true;
                isCashier = true;
                binding.isAdmin.setEnabled(true);
                binding.isSeller.setEnabled(true);
                binding.isCashier.setEnabled(true);
            }
        });
        binding.isSeller.setOnCheckedChangeListener((compoundButton, b) -> {

            if (isSeller == binding.isSeller.isChecked()) {
                UserRole = "Seller";
                binding.isAdmin.setEnabled(false);
                binding.isManager.setEnabled(false);
                binding.isCashier.setEnabled(false);
                isAdmin = false;
                isManager = false;
                isCashier = false;
            } else {
                UserRole = null;
                isAdmin = true;
                isManager = true;
                isCashier = true;
                binding.isAdmin.setEnabled(true);
                binding.isCashier.setEnabled(true);
                binding.isManager.setEnabled(true);
            }
        });
        binding.isCashier.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isCashier == binding.isCashier.isChecked()) {
                UserRole = "Cashier";
                binding.isAdmin.setEnabled(false);
                binding.isManager.setEnabled(false);
                binding.isSeller.setEnabled(false);
                isAdmin = false;
                isSeller = false;
                isManager = false;
            } else {
                UserRole = null;
                isAdmin = true;
                isSeller = true;
                isManager = true;
                binding.isAdmin.setEnabled(true);
                binding.isManager.setEnabled(true);
                binding.isSeller.setEnabled(true);
            }
        });

        binding.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canDiscount = binding.canDiscount.isChecked()) {
                Toast.makeText(this, "canDiscount : " + canDiscount, Toast.LENGTH_SHORT).show();
            } else {
                canDiscount = false;
            }
        });
        binding.canAddCategory.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canAddCategory = binding.canAddCategory.isChecked()) {
                Toast.makeText(this, "canAddCategory : " + canAddCategory, Toast.LENGTH_SHORT).show();
            } else {
                canAddCategory = false;
            }

        });
        binding.canAddItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canAddItem = binding.canAddItem.isChecked()) {
                Toast.makeText(this, "canAddItem : " + canAddItem, Toast.LENGTH_SHORT).show();
            } else {
                canAddItem = false;
            }
        });

        binding.canDeleteItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canDeleteItem = binding.canDeleteItem.isChecked()) {
                Toast.makeText(this, "canDeleteItem : " + canDeleteItem, Toast.LENGTH_SHORT).show();
            } else {
                canDeleteItem = false;
            }
        });
        binding.canUpdateItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canUpdateItem = binding.canUpdateItem.isChecked()) {
                Toast.makeText(this, "canUpdateItem : " + canUpdateItem, Toast.LENGTH_SHORT).show();
            } else {
                canUpdateItem = false;
            }
        });
        binding.canDoAll.setOnCheckedChangeListener((compoundButton, b) -> {
            if (AllowAllPermission = binding.canDoAll.isChecked()) {
                binding.canAddItem.setChecked(false);
                binding.canAddCategory.setChecked(false);
                binding.canDiscount.setChecked(false);
                binding.canUpdateItem.setChecked(false);
                binding.canDeleteItem.setChecked(false);

                binding.canAddCategory.setEnabled(false);
                binding.canAddItem.setEnabled(false);
                binding.canDiscount.setEnabled(false);
                binding.canDeleteItem.setEnabled(false);
                binding.canUpdateItem.setEnabled(false);

                canDiscount = true;
                canAddCategory = true;
                canAddItem = true;
                canUpdateItem = true;
                canDeleteItem = true;
            } else {
                canDiscount = false;
                canAddCategory = false;
                canAddItem = false;
                canUpdateItem = false;
                canDeleteItem = false;

                binding.canAddCategory.setEnabled(true);
                binding.canAddItem.setEnabled(true);
                binding.canDiscount.setEnabled(true);
                binding.canDeleteItem.setEnabled(true);
                binding.canUpdateItem.setEnabled(true);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_user:
                binding.showListAllUser.setVisibility(View.GONE);
                binding.layoutAddUser.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_user:
                break;
            case R.id.delete_user:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}