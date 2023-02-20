package com.example.pos.account;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.ActivityManageAccountBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManageAccountActivity extends AppCompatActivity {

    private static final String TAG = "User Role";
    ActivityManageAccountBinding binding;

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
    Boolean canDiscount,
            canUpdateItem,
            canAddItem,
            canAddCategory,
            canDeleteItem,
            AllowAllPermission;

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
        binding.btnSaveCreateUser.setOnClickListener(this::OnSaveCreateUser);
        OnStateCheckBoxUserRole();
    }

    private void OnSaveCreateUser(View view) {
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = simpleFormatter.format(current);
        String Firstname = binding.addFirstName.getText().toString();
        String Lastname = binding.addLastName.getText().toString();
        String Username = binding.addUserName.getText().toString();
        String Password = binding.addUserPassword.getText().toString();
        UserAccount userAccount = new UserAccount(Firstname, Lastname, Username, Password, isAdmin,
                isManager, isSeller, isCashier, canDiscount, canUpdateItem, canAddItem, canAddCategory,
                canDeleteItem, formattedDate);
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().createUser(userAccount);
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
            } else {
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
                binding.isAdmin.setEnabled(false);
                binding.isSeller.setEnabled(false);
                binding.isCashier.setEnabled(false);
                isSeller = false;
                isAdmin = false;
                isCashier = false;
            } else {
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
                binding.isAdmin.setEnabled(false);
                binding.isManager.setEnabled(false);
                binding.isCashier.setEnabled(false);
                isAdmin = false;
                isManager = false;
                isCashier = false;
            } else {
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
                binding.isAdmin.setEnabled(false);
                binding.isManager.setEnabled(false);
                binding.isSeller.setEnabled(false);
                isAdmin = false;
                isSeller = false;
                isManager = false;
            } else {
                isAdmin = true;
                isSeller = true;
                isManager = true;
                binding.isAdmin.setEnabled(true);
                binding.isManager.setEnabled(true);
                binding.isSeller.setEnabled(true);
            }
        });

        binding.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> {
            canDiscount = binding.canDiscount.isChecked();
            Toast.makeText(this, "canDiscount : " + canDiscount, Toast.LENGTH_SHORT).show();
        });
        binding.canAddCategory.setOnCheckedChangeListener((compoundButton, b) -> {
            canAddCategory = binding.canAddCategory.isChecked();
            Toast.makeText(this, "canAddCategory : " + canAddCategory, Toast.LENGTH_SHORT).show();
        });
        binding.canAddItem.setOnCheckedChangeListener((compoundButton, b) -> {
            canAddItem = binding.canAddItem.isChecked();
            Toast.makeText(this, "canAddItem : " + canAddItem, Toast.LENGTH_SHORT).show();
        });

        binding.canDeleteItem.setOnCheckedChangeListener((compoundButton, b) -> {
            canDeleteItem = binding.canDeleteItem.isChecked();
            Toast.makeText(this, "canDeleteItem : " + canDeleteItem, Toast.LENGTH_SHORT).show();
        });
        binding.canUpdateItem.setOnCheckedChangeListener((compoundButton, b) -> {
            canUpdateItem = binding.canUpdateItem.isChecked();
            Toast.makeText(this, "canUpdateItem : " + canUpdateItem, Toast.LENGTH_SHORT).show();
        });
        binding.canDoAll.setOnCheckedChangeListener((compoundButton, b) -> {
            if (AllowAllPermission = binding.canDoAll.isChecked()) {
                binding.canAddItem.setChecked(false);
                binding.canAddCategory.setChecked(false);
                binding.canDiscount.setChecked(false);
                binding.canUpdateItem.setChecked(false);
                binding.canDeleteItem.setChecked(false);
                canDiscount = true;
                canAddCategory = true;
                canAddItem = true;
                canUpdateItem = true;
                canDeleteItem = true;
                Toast.makeText(this, "canDiscount : " + canDiscount +
                        "\ncanAddItem : :" + canAddItem +
                        "\ncanAddCategory : " + canAddCategory +
                        "\ncanUpdateItem : " + canUpdateItem +
                        "\ncanDeleteItem : " + canDeleteItem, Toast.LENGTH_SHORT).show();
            } else {
                canDiscount = false;
                canAddCategory = false;
                canAddItem = false;
                canUpdateItem = false;
                canDeleteItem = false;
                Toast.makeText(this, "canDiscount : " + canDiscount +
                        "\ncanAddItem : :" + canAddItem +
                        "\ncanAddCategory : " + canAddCategory +
                        "\ncanUpdateItem : " + canUpdateItem +
                        "\ncanDeleteItem : " + canDeleteItem, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}