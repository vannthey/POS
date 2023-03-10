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
import com.example.pos.Configure.DateHelper;
import com.example.pos.R;
import com.example.pos.Configure.SharedPrefHelper;
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
            finish();
        });
        setTitle("Account Management");
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        binding.formUser.btnSaveCreateUser.setOnClickListener(v -> SaveUserAccount());
        binding.formUser.btnCancelCreateUser.setOnClickListener(v -> OnUpdateUI());
        binding.formUser.btnUpdateUser.setOnClickListener(v -> OnUpdateUser());
        binding.formUser.btnDeleteUser.setOnClickListener(v -> OnDeleteUser());
        binding.formUser.userImageProfile.setOnClickListener(v -> OnGetImage());
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
        binding.formUser.formAddUser.setVisibility(View.VISIBLE);
    }


    private void DeleteUpdate() {
        binding.formUser.btnDeleteUser.setVisibility(View.VISIBLE);
        binding.formUser.btnUpdateUser.setVisibility(View.VISIBLE);
        binding.formUser.btnSaveCreateUser.setVisibility(View.GONE);
    }


    private void OnUpdateUser() {
        if (String.valueOf(binding.formUser.txtFirstName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtLastName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtUserName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtPassword.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtAddress.getText()).isEmpty()) {
            Toast.makeText(this, R.string.Please_Input_User, Toast.LENGTH_SHORT).show();
        } else {
            Firstname = String.valueOf(binding.formUser.txtFirstName.getText());
            Lastname = String.valueOf(binding.formUser.txtLastName.getText());
            Username = String.valueOf(binding.formUser.txtUserName.getText());
            Password = String.valueOf(binding.formUser.txtPassword.getText());
            Address = String.valueOf(binding.formUser.txtAddress.getText());
            if (DateOfBirth == null) {
                DateOfBirth = DateHelper.getCurrentDate();
            }
            viewModel.updateUserById(Firstname, Lastname, Username, Password, DateOfBirth, Address, UserSex, UserRole,
                    profilePath, canDiscount, canChangePrice, UserId);
            OnUpdateUI();
        }


    }

    private void SetDateOfBirth() {
        binding.formUser.txtDateOfBirth.setText(DateHelper.getCurrentDate());
        binding.formUser.txtDateOfBirth.setOnClickListener(v -> {
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
                        month = "Mar";
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
                binding.formUser.txtDateOfBirth.setText(DateOfBirth);
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
                    binding.formUser.txtFirstName.setText(userAccounts.get(i).getFirstname());
                    binding.formUser.txtLastName.setText(userAccounts.get(i).getLastname());
                    binding.formUser.txtPassword.setText(userAccounts.get(i).getPassword());
                    binding.formUser.txtUserName.setText(userAccounts.get(i).getUsername());
                    Glide.with(this).load(userAccounts.get(i).getProfilePath()).into(binding.formUser.userImageProfile);
                    if (userAccounts.get(i).getSex().contains("Male")) {
                        binding.formUser.isMale.setChecked(true);
                    } else if (userAccounts.get(i).getSex().contains("Female")) {
                        binding.formUser.isFemale.setChecked(true);
                    }
                    binding.formUser.txtDateOfBirth.setText(userAccounts.get(i).getDOB());
                    binding.formUser.txtAddress.setText(userAccounts.get(i).getAddress());
                    switch (userAccounts.get(i).getUserRole()) {
                        case "Admin":
                            binding.formUser.isAdmin.setChecked(true);
                            break;
                        case "Seller":
                            binding.formUser.isSeller.setChecked(true);
                            break;
                        case "Cashier":
                            binding.formUser.isCashier.setChecked(true);
                            break;
                        case "Manager":
                            binding.formUser.isManager.setChecked(true);
                            break;
                    }
                    profilePath = userAccounts.get(i).getProfilePath();
                    if (userAccounts.get(i).getCanDiscount() == null) {
                        binding.formUser.canDiscount.setChecked(false);
                    } else if (userAccounts.get(i).getCanChangePrice() == null) {
                        binding.formUser.canChangePrice.setChecked(false);
                    } else {
                        binding.formUser.canDiscount.setChecked(true);
                        binding.formUser.canChangePrice.setChecked(true);
                    }
                    FormCreateUser();
                    DeleteUpdate();
                });
            }
        });
    }

    private void SaveUserAccount() {
        if (String.valueOf(binding.formUser.txtFirstName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtLastName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtUserName.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtPassword.getText()).isEmpty()
                || String.valueOf(binding.formUser.txtAddress.getText()).isEmpty()) {
            Toast.makeText(this, R.string.Please_Input_User, Toast.LENGTH_SHORT).show();
        } else {
            Firstname = String.valueOf(binding.formUser.txtFirstName.getText());
            Lastname = String.valueOf(binding.formUser.txtLastName.getText());
            Username = String.valueOf(binding.formUser.txtUserName.getText());
            Password = String.valueOf(binding.formUser.txtPassword.getText());
            Address = String.valueOf(binding.formUser.txtAddress.getText());
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
        binding.formUser.accountPosition.setOnCheckedChangeListener((radioGroup, i) -> {
            if (isAdmin == binding.formUser.isAdmin.isChecked()) {
                UserRole = "Admin";
                canDiscount = true;
                canChangePrice = true;
                binding.formUser.canDiscount.setEnabled(false);
                binding.formUser.canChangePrice.setEnabled(false);
            } else if (isSeller == binding.formUser.isSeller.isChecked()) {
                UserRole = "Seller";
                binding.formUser.canDiscount.setEnabled(true);
                binding.formUser.canChangePrice.setEnabled(true);
            } else if (isCashier == binding.formUser.isCashier.isChecked()) {
                binding.formUser.canDiscount.setEnabled(true);
                binding.formUser.canChangePrice.setEnabled(true);
                UserRole = "Cashier";
            } else if (isManager == binding.formUser.isManager.isChecked()) {
                binding.formUser.canDiscount.setEnabled(true);
                binding.formUser.canChangePrice.setEnabled(true);
                UserRole = "Manager";
            }
        });
        binding.formUser.userAccountSex.setOnCheckedChangeListener((radioGroup, i) -> {
            if (binding.formUser.isMale.isChecked()) {
                UserSex = "Male";
            } else if (binding.formUser.isFemale.isChecked()) {
                UserSex = "Female";
            }
        });
        binding.formUser.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> canDiscount = binding.formUser.canDiscount.isChecked());
        binding.formUser.canChangePrice.setOnCheckedChangeListener((compoundButton, b) -> canChangePrice =
                binding.formUser.canChangePrice.isChecked());
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
                    binding.formUser.userImageProfile.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, "No Image Pick", Toast.LENGTH_SHORT).show();
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });


    private void OnUpdateUI() {
        binding.listAllUser.setVisibility(View.VISIBLE);
        binding.formUser.formAddUser.setVisibility(View.GONE);
        binding.formUser.userImageProfile.setImageResource(R.drawable.ic_image);
        binding.formUser.canDiscount.setChecked(false);
        binding.formUser.canChangePrice.setChecked(false);
        binding.formUser.txtFirstName.setText(null);
        binding.formUser.txtLastName.setText(null);
        binding.formUser.txtUserName.setText(null);
        binding.formUser.txtPassword.setText(null);
        binding.formUser.txtAddress.setText(null);
        binding.formUser.btnDeleteUser.setVisibility(View.GONE);
        binding.formUser.btnUpdateUser.setVisibility(View.GONE);
        binding.formUser.btnSaveCreateUser.setVisibility(View.VISIBLE);
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