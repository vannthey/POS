package com.example.pos.account;

import android.app.DatePickerDialog;
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

import com.example.pos.CurrentDateHelper;
import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.Database.POSDatabase;
import com.example.pos.R;
import com.example.pos.databinding.ActivityManageAccountBinding;

import java.util.List;

public class ManageAccountActivity extends AppCompatActivity {
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
            canDeleteItem = false;
    /*
    Sex
     */
    Boolean isMale = true,
            isFemale = true;
    String UserSex;
    int UserId;
    UserAccount userAccount;
    String DateOfBirth;
    String day;
    String month;
    String year;
    List<UserAccount> ListUsers;
    Handler handler;

    String Firstname;
    String Lastname;
    String Username;
    String Password;
    String Address;

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
        handler = new Handler();
        binding.btnSaveCreateUser.setOnClickListener(v -> OnSaveCreateUser());
        binding.btnCancelCreateUser.setOnClickListener(v -> OnCancelUser());
        binding.btnUpdateUser.setOnClickListener(v -> OnUpdateUser());
        binding.btnDeleteUser.setOnClickListener(v -> OnDeleteUser());
        OnCallAllUserFromDB();
        OnSetUserDateOfBird();
        OnSelectUser();
        OnStateCheckBox();
    }

    private void OnCancelUser() {
        OnClearDataInView();
        OnHideFormAddUser();
    }

    private void OnDeleteUser() {
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().deleteUserById(UserId);
            handler.post(() -> {
                Toast.makeText(this, "User " + binding.txtUserName.getText() + " have been " +
                                "deleted",
                        Toast.LENGTH_SHORT).show();
                OnHideBtnDeleteUpdate();
                OnHideFormAddUser();
                OnClearDataInView();
                OnCallAllUserFromDB();
            });
        }).start();

    }

    private void OnSelectUser() {
        binding.listAllUser.setOnItemClickListener((adapterView, view, i, l) -> {
            OnShowFormAddUser();
            OnShowBtnDeleteUpdate();
            UserId = ListUsers.get(i).userId;
            binding.txtFirstName.setText(ListUsers.get(i).getFirstname());
            binding.txtLastName.setText(ListUsers.get(i).getLastname());
            binding.txtPassword.setText(ListUsers.get(i).getPassword());
            binding.txtUserName.setText(ListUsers.get(i).getUsername());
            if (ListUsers.get(i).getSex() == null) {
                binding.isMale.setChecked(false);
                binding.isFemale.setChecked(false);
            } else if (ListUsers.get(i).getSex().equals("Male")) {
                binding.isMale.setChecked(true);
            } else if (ListUsers.get(i).getSex().equals("Female")) {
                binding.isFemale.setChecked(true);
            }
            binding.txtDateOfBirth.setText(ListUsers.get(i).getDOB());
            binding.txtAddress.setText(ListUsers.get(i).getAddress());
            switch (ListUsers.get(i).getUserRole()) {
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

            binding.canDiscount.setChecked(ListUsers.get(i).getCanDiscount());
            binding.canUpdateItem.setChecked(ListUsers.get(i).getCanUpdate());
            binding.canAddItem.setChecked(ListUsers.get(i).getCanAddItem());
            binding.canAddCategory.setChecked(ListUsers.get(i).getCanAddCategory());
            binding.canDeleteItem.setChecked(ListUsers.get(i).getCanDeleteItem());
        });
    }

    private void OnGetAllDataFromForm() {

        Firstname = String.valueOf(binding.txtFirstName.getText());
        Lastname = String.valueOf(binding.txtLastName.getText());
        Username = String.valueOf(binding.txtUserName.getText());
        Password = String.valueOf(binding.txtPassword.getText());
        Address = String.valueOf(binding.txtAddress.getText());
        if (DateOfBirth == null) {
            DateOfBirth = CurrentDateHelper.getCurrentDate();
        }

    }

    private void OnShowFormAddUser() {
        binding.listAllUser.setVisibility(View.GONE);
        binding.formAddUser.setVisibility(View.VISIBLE);
    }

    private void OnHideBtnDeleteUpdate() {
        binding.btnDeleteUser.setVisibility(View.GONE);
        binding.btnUpdateUser.setVisibility(View.GONE);
        binding.btnSaveCreateUser.setVisibility(View.VISIBLE);
    }

    private void OnShowBtnDeleteUpdate() {
        binding.btnDeleteUser.setVisibility(View.VISIBLE);
        binding.btnUpdateUser.setVisibility(View.VISIBLE);
        binding.btnSaveCreateUser.setVisibility(View.GONE);
    }

    private void OnHideFormAddUser() {
        binding.listAllUser.setVisibility(View.VISIBLE);
        binding.formAddUser.setVisibility(View.GONE);
    }

    private void OnUpdateUser() {
        OnGetAllDataFromForm();
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().updateUserById(Firstname,
                    Lastname, Username, Password, DateOfBirth, Address, UserSex, UserRole, canDiscount,
                    canUpdateItem
                    , canAddItem, canAddCategory, canDeleteItem, UserId);
            handler.post(() -> {
                Toast.makeText(this, "User have been updated", Toast.LENGTH_SHORT).show();
                OnCallAllUserFromDB();
            });
        }).start();
        OnHideBtnDeleteUpdate();
        OnHideFormAddUser();
        OnClearDataInView();
    }

    private void OnSetUserDateOfBird() {
        binding.txtDateOfBirth.setText(CurrentDateHelper.getCurrentDate());
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

    private void OnAnimationChangeLayout() {
        TransitionManager.beginDelayedTransition(binding.formAddUser, transition);
        transition = new Slide();
        transition.setDuration(6000);
    }

    private void OnClearDataInView() {
        binding.isMale.setChecked(false);
        binding.isFemale.setChecked(false);
        binding.canDiscount.setChecked(false);
        binding.canUpdateItem.setChecked(false);
        binding.canAddItem.setChecked(false);
        binding.canAddCategory.setChecked(false);
        binding.canDeleteItem.setChecked(false);
        binding.isAdmin.setChecked(false);
        binding.isCashier.setChecked(false);
        binding.isManager.setChecked(false);
        binding.isSeller.setChecked(false);
        binding.txtFirstName.setText("");
        binding.txtLastName.setText("");
        binding.txtUserName.setText("");
        binding.txtPassword.setText("");
        binding.txtAddress.setText("");
        binding.txtDateOfBirth.setText("");
    }

    private void OnCallAllUserFromDB() {
        new Thread(() -> {
            ListUsers =
                    POSDatabase.getInstance(getApplicationContext()).getDao().userAccount();
            handler.post(() -> binding.listAllUser.setAdapter(new AdapterAccountManager(ListUsers,
                    this)));
        }).start();
    }

    private void OnSaveCreateUser() {
        OnGetAllDataFromForm();
        userAccount = new UserAccount(Firstname, Lastname, UserSex, DateOfBirth, Address, Username,
                Password, UserRole, canDiscount, canUpdateItem, canAddItem, canAddCategory,
                canDeleteItem, CurrentDateHelper.getCurrentDate());
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().createUser(userAccount);
            handler.post(() -> {
                OnHideFormAddUser();
                OnClearDataInView();
                OnCallAllUserFromDB();
            });
        }).start();
    }

    private void OnStateCheckBox() {
        binding.isMale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isMale = binding.isMale.isChecked()) {
                UserSex = "Male";
                binding.isFemale.setEnabled(false);
            } else {
                UserSex = null;
                binding.isFemale.setEnabled(true);
            }
        });
        binding.isFemale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isFemale = binding.isFemale.isChecked()) {
                UserSex = "Female";
                binding.isMale.setEnabled(false);
            } else {
                UserSex = null;
                binding.isMale.setEnabled(true);
            }
        });
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

        binding.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> canDiscount = binding.canDiscount.isChecked());
        binding.canAddCategory.setOnCheckedChangeListener((compoundButton, b) -> canAddCategory = binding.canAddCategory.isChecked());
        binding.canAddItem.setOnCheckedChangeListener((compoundButton, b) -> canAddItem = binding.canAddItem.isChecked());

        binding.canDeleteItem.setOnCheckedChangeListener((compoundButton, b) -> canDeleteItem = binding.canDeleteItem.isChecked());
        binding.canUpdateItem.setOnCheckedChangeListener((compoundButton, b) -> canUpdateItem = binding.canUpdateItem.isChecked());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_user) {
            OnShowFormAddUser();
            OnClearDataInView();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}