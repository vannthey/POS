package com.example.pos.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.DateHelper;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
import com.example.pos.databinding.ActivityManageAccountBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;

public class ManageAccountActivity extends AppCompatActivity {
    ActivityManageAccountBinding binding;
    AccountViewModel viewModel;
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
    Boolean canDiscount, canChangePrice;
    String UserSex;
    int UserId;
    UserAccount userAccount;
    String DateOfBirth;
    String day;
    String month;
    String year;

    String Firstname;
    String Lastname;
    String Username;
    String Password;
    String Address;
    Uri uri;
    File file;
    String profilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAccountBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.customActionbarManageAccount.customActionbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.customActionbarManageAccount.customActionbar.setNavigationOnClickListener(v -> {
            setTitle(R.string.app_default);
            finish();
        });
        setTitle("Account Management");
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        binding.btnSaveCreateUser.setOnClickListener(v -> SaveUserAccount());
        binding.btnCancelCreateUser.setOnClickListener(v -> OnUpdateUI());
        binding.btnUpdateUser.setOnClickListener(v -> OnUpdateUser());
        binding.btnDeleteUser.setOnClickListener(v -> OnDeleteUser());
        binding.userImageProfile.setOnClickListener(v -> OnGetImage());
        GetAllUser();
        SetDateOfBirth();
        OnStateCheckBox();
    }


    private void OnDeleteUser() {
        viewModel.deleteUserById(UserId);
        OnUpdateUI();
    }

    private void FormCreateUser() {
        binding.listAllUser.setVisibility(View.GONE);
        binding.formAddUser.setVisibility(View.VISIBLE);
    }


    private void DeleteUpdate() {
        binding.btnDeleteUser.setVisibility(View.VISIBLE);
        binding.btnUpdateUser.setVisibility(View.VISIBLE);
        binding.btnSaveCreateUser.setVisibility(View.GONE);
    }


    private void OnUpdateUser() {
        if (String.valueOf(binding.txtFirstName.getText()).isEmpty()
                || String.valueOf(binding.txtLastName.getText()).isEmpty()
                || String.valueOf(binding.txtUserName.getText()).isEmpty()
                || String.valueOf(binding.txtPassword.getText()).isEmpty()
                || String.valueOf(binding.txtAddress.getText()).isEmpty()) {
            Toast.makeText(this, R.string.Please_Input_User, Toast.LENGTH_SHORT).show();
        } else {
            Firstname = String.valueOf(binding.txtFirstName.getText());
            Lastname = String.valueOf(binding.txtLastName.getText());
            Username = String.valueOf(binding.txtUserName.getText());
            Password = String.valueOf(binding.txtPassword.getText());
            Address = String.valueOf(binding.txtAddress.getText());
            if (DateOfBirth == null) {
                DateOfBirth = DateHelper.getCurrentDate();
            }
            viewModel.updateUserById(Firstname, Lastname, Username, Password, DateOfBirth, Address, UserSex, UserRole,
                    profilePath, canDiscount, canChangePrice, UserId);
            OnUpdateUI();
        }


    }

    private void SetDateOfBirth() {
        binding.txtDateOfBirth.setText(DateHelper.getCurrentDate());
        binding.txtDateOfBirth.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(this);
            dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                switch (i1 + 1) {
                    case 1:
                        month = "Jan";
                        break;
                    case 2:
                        month = "Feb";
                        break;
                    case 3:
                        month = "Mac";
                        break;
                    case 4:
                        month = "Apr";
                        break;
                    case 5:
                        month = "May";
                        break;
                    case 6:
                        month = "Jun";
                        break;
                    case 7:
                        month = "Jul";
                        break;
                    case 8:
                        month = "Aug";
                        break;
                    case 9:
                        month = "Set";
                        break;
                    case 10:
                        month = "Oct";
                        break;
                    case 11:
                        month = "Nov";
                        break;
                    case 12:
                        month = "Dec";
                        break;
                }
                year = String.valueOf(i);
                day = String.valueOf(i2);
                DateOfBirth = day + "-" + month + "-" + year;
                binding.txtDateOfBirth.setText(DateOfBirth);
            });
            dialog.show();


        });
    }


    private void GetAllUser() {
        viewModel.userAccount().observe(this, userAccounts -> {
            if (userAccounts.size() != 0) {
                binding.listAllUser.setAdapter(new AdapterAccountManager(userAccounts, this));
                binding.listAllUser.setOnItemClickListener((adapterView, view, i, l) -> {
                    UserId = userAccounts.get(i).getUserId();
                    binding.txtFirstName.setText(userAccounts.get(i).getFirstname());
                    binding.txtLastName.setText(userAccounts.get(i).getLastname());
                    binding.txtPassword.setText(userAccounts.get(i).getPassword());
                    binding.txtUserName.setText(userAccounts.get(i).getUsername());
                    Glide.with(this).load(userAccounts.get(i).getProfilePath()).into(binding.userImageProfile);
                    if (userAccounts.get(i).getSex().contains("Male")) {
                        binding.isMale.setChecked(true);
                    } else if (userAccounts.get(i).getSex().contains("Female")) {
                        binding.isFemale.setChecked(true);
                    }
                    binding.txtDateOfBirth.setText(userAccounts.get(i).getDOB());
                    binding.txtAddress.setText(userAccounts.get(i).getAddress());
                    switch (userAccounts.get(i).getUserRole()) {
                        case "Admin":
                            binding.isAdmin.setChecked(true);
                            break;
                        case "Seller":
                            binding.isSeller.setChecked(true);
                            break;
                        case "Cashier":
                            binding.isCashier.setChecked(true);
                            break;
                        case "Manager":
                            binding.isManager.setChecked(true);
                            break;
                    }
                    profilePath = userAccounts.get(i).getProfilePath();
                    if (userAccounts.get(i).getCanDiscount() == null) {
                        binding.canDiscount.setChecked(false);
                    } else if (userAccounts.get(i).getCanChangePrice() == null) {
                        binding.canChangePrice.setChecked(false);
                    } else {
                        binding.canDiscount.setChecked(true);
                        binding.canChangePrice.setChecked(true);
                    }
                    FormCreateUser();
                    DeleteUpdate();
                });
            }
        });
    }

    private void SaveUserAccount() {
        if (String.valueOf(binding.txtFirstName.getText()).isEmpty()
                || String.valueOf(binding.txtLastName.getText()).isEmpty()
                || String.valueOf(binding.txtUserName.getText()).isEmpty()
                || String.valueOf(binding.txtPassword.getText()).isEmpty()
                || String.valueOf(binding.txtAddress.getText()).isEmpty()) {
            Toast.makeText(this, R.string.Please_Input_User, Toast.LENGTH_SHORT).show();
        } else {
            Firstname = String.valueOf(binding.txtFirstName.getText());
            Lastname = String.valueOf(binding.txtLastName.getText());
            Username = String.valueOf(binding.txtUserName.getText());
            Password = String.valueOf(binding.txtPassword.getText());
            Address = String.valueOf(binding.txtAddress.getText());
            if (DateOfBirth == null) {
                DateOfBirth = DateHelper.getCurrentDate();
            }
            userAccount = new UserAccount(Firstname, Lastname, UserSex, DateOfBirth, Address, Username, Password, UserRole,
                    profilePath, canDiscount, canChangePrice,
                    SharedPrefHelper.getInstance().getSaveUserLoginName(this), DateHelper.getCurrentDate());

            viewModel.createUser(userAccount);
            OnUpdateUI();

        }

    }

    private void OnStateCheckBox() {
        binding.accountPosition.setOnCheckedChangeListener((radioGroup, i) -> {
            if (isAdmin == binding.isAdmin.isChecked()) {
                UserRole = "Admin";
                canDiscount = true;
                canChangePrice = true;
                binding.canDiscount.setEnabled(false);
                binding.canChangePrice.setEnabled(false);
            } else if (isSeller == binding.isSeller.isChecked()) {
                UserRole = "Seller";
                binding.canDiscount.setEnabled(true);
                binding.canChangePrice.setEnabled(true);
            } else if (isCashier == binding.isCashier.isChecked()) {
                binding.canDiscount.setEnabled(true);
                binding.canChangePrice.setEnabled(true);
                UserRole = "Cashier";
            } else if (isManager == binding.isManager.isChecked()) {
                binding.canDiscount.setEnabled(true);
                binding.canChangePrice.setEnabled(true);
                UserRole = "Manager";
            }
        });
        binding.userAccountSex.setOnCheckedChangeListener((radioGroup, i) -> {
            if (binding.isMale.isChecked()) {
                UserSex = "Male";
            } else if (binding.isFemale.isChecked()) {
                UserSex = "Female";
            }
        });
        binding.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> canDiscount = binding.canDiscount.isChecked());
        binding.canChangePrice.setOnCheckedChangeListener((compoundButton, b) -> canChangePrice =
                binding.canChangePrice.isChecked());
    }

    private void OnGetImage() {
        launcher.launch(ImagePicker.Companion.with(this)
                .maxResultSize(1080, 1080, true)
                .crop().galleryOnly()
                .createIntent());

    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    assert result.getData() != null;
                    uri = result.getData().getData();
                    file = new File(uri.getPath());
                    profilePath = file.toString();
                    // Use the uri to load the image
                    binding.userImageProfile.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, "No Image Pick", Toast.LENGTH_SHORT).show();
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });


    private void OnUpdateUI() {
        binding.listAllUser.setVisibility(View.VISIBLE);
        binding.formAddUser.setVisibility(View.GONE);
        binding.userImageProfile.setImageResource(R.drawable.ic_image);
        binding.canDiscount.setChecked(false);
        binding.canChangePrice.setChecked(false);
        binding.txtFirstName.setText(null);
        binding.txtLastName.setText(null);
        binding.txtUserName.setText(null);
        binding.txtPassword.setText(null);
        binding.txtAddress.setText(null);
        binding.btnDeleteUser.setVisibility(View.GONE);
        binding.btnUpdateUser.setVisibility(View.GONE);
        binding.btnSaveCreateUser.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_user) {
            FormCreateUser();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}