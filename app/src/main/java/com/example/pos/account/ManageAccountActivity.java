package com.example.pos.account;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pos.R;
import com.example.pos.databinding.ActivityManageAccountBinding;

public class ManageAccountActivity extends AppCompatActivity {

    private static final String TAG = "User Role";
    ActivityManageAccountBinding binding;

    /*
    Position
     */
    Boolean isAdmin = false,
            isSeller = false,
            isCashier = false,
            isManager = false;
    /*
    permission
     */
    Boolean canDiscount,
            canUpdateItem,
            canAddItem,
            canAddCategory,
            canDeleteItem,
            AllowAllPermission;

    /*
    log in information
     */
    String Username;
    String Password;

    /*
    User information
     */
    String Firstname;
    String Lastname;


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

    }

    private void OnGetAllInformation() {

        Firstname = binding.addFirstName.getText().toString();
        Lastname = binding.addLastName.getText().toString();
        Username = binding.addUserName.getText().toString();
        Password = binding.addUserPassword.getText().toString();


    }

    private void OnStateCheckBoxUserRole() {
        binding.isAdmin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isAdmin == binding.isAdmin.isChecked()) {
                binding.isManager.setEnabled(false);
                binding.isSeller.setEnabled(false);
                binding.isCashier.setEnabled(false);
            } else {
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
            } else {
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
            } else {
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
            } else {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}