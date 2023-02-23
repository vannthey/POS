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
    /*
    Sex
     */
    Boolean isMale = true,
            isFemale = true;
    String UserSex;
    UserAccount userAccount;
    String DateOfBirth;
    String day;
    String month;
    String year;
    List<UserAccount> userAccountList;
    Handler handler;
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
        OnStateCheckBox();
        OnCallAllUserFromDB();
        OnSetUserDateOfBird();
    }

    private void OnSetUserDateOfBird() {
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(current);
        binding.userDateOfBirth.setText(currentDate);
        binding.userDateOfBirth.setOnClickListener(v -> {
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
                binding.userDateOfBirth.setText(DateOfBirth);
            });
            dialog.show();


        });
    }

    private void OnAnimationChangeLayout() {
        TransitionManager.beginDelayedTransition(binding.layoutAddUser, transition);
        transition = new Slide();
        transition.setDuration(6000);
    }

    private void OnCancelCreateUser(View view) {
        binding.userMale.setChecked(false);
        binding.userFemale.setChecked(false);
        binding.canDoAll.setChecked(false);
        binding.canDiscount.setChecked(false);
        binding.canUpdateItem.setChecked(false);
        binding.canAddItem.setChecked(false);
        binding.canAddCategory.setChecked(false);
        binding.canDeleteItem.setChecked(false);
        binding.isAdmin.setChecked(false);
        binding.isCashier.setChecked(false);
        binding.isManager.setChecked(false);
        binding.isSeller.setChecked(false);
        binding.addFirstName.setText("");
        binding.addLastName.setText("");
        binding.addUserName.setText("");
        binding.addUserPassword.setText("");
        binding.userAddress.setText("");
        binding.userDateOfBirth.setText("");
        binding.showListAllUser.setVisibility(View.VISIBLE);
        binding.layoutAddUser.setVisibility(View.GONE);
    }

    private void OnCallAllUserFromDB() {
        handler = new Handler();
        new Thread(() -> {
            userAccountList =
                    POSDatabase.getInstance(getApplicationContext()).getDao().userAccount();
            handler.post(() ->{
                binding.showListAllUser.setAdapter(new AdapterAccountManager(userAccountList,
                        this));
                binding.showListAllUser.setOnItemClickListener((adapterView, view, i, l) -> {
                    Toast.makeText(this, ""+userAccountList.get(i).toString(), Toast.LENGTH_SHORT).show();
                });
            });
        }).start();
    }

    private void OnSaveCreateUser(View view) {
        Handler handler = new Handler();
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = simpleFormatter.format(current);
        String Firstname = String.valueOf(binding.addFirstName.getText());
        String Lastname = String.valueOf(binding.addLastName.getText());
        String Username = String.valueOf(binding.addUserName.getText());
        String Password = String.valueOf(binding.addUserPassword.getText());
        String Address = String.valueOf(binding.userAddress.getText());
        userAccount = new UserAccount(Firstname, Lastname, UserSex, DateOfBirth, Address, Username,
                Password, UserRole, canDiscount, canUpdateItem, canAddItem, canAddCategory,
                canDeleteItem, formattedDate);
        new Thread(() -> {
            POSDatabase.getInstance(getApplicationContext()).getDao().createUser(userAccount);
            handler.post(() -> {
                binding.layoutAddUser.setVisibility(View.GONE);
                binding.showListAllUser.setVisibility(View.VISIBLE);
                binding.userMale.setChecked(false);
                binding.userFemale.setChecked(false);
                binding.canDoAll.setChecked(false);
                binding.canDiscount.setChecked(false);
                binding.canUpdateItem.setChecked(false);
                binding.canAddItem.setChecked(false);
                binding.canAddCategory.setChecked(false);
                binding.canDeleteItem.setChecked(false);
                binding.isAdmin.setChecked(false);
                binding.isCashier.setChecked(false);
                binding.isManager.setChecked(false);
                binding.isSeller.setChecked(false);
                binding.addFirstName.setText("");
                binding.addLastName.setText("");
                binding.addUserName.setText("");
                binding.addUserPassword.setText("");
                binding.userAddress.setText("");
                binding.userDateOfBirth.setText("");
                OnCallAllUserFromDB();
            });
        }).start();
    }

    private void OnStateCheckBox() {
        binding.userMale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isMale = binding.userMale.isChecked()) {
                UserSex = "Male";
                binding.userFemale.setEnabled(false);
            } else {
                UserSex = null;
                binding.userFemale.setEnabled(true);
            }
        });
        binding.userFemale.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isFemale = binding.userFemale.isChecked()) {
                UserSex = "Female";
                binding.userMale.setEnabled(false);
            } else {
                UserSex = null;
                binding.userMale.setEnabled(true);
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

        binding.canDiscount.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canDiscount = binding.canDiscount.isChecked()) {
            } else {
                canDiscount = false;
            }
        });
        binding.canAddCategory.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canAddCategory = binding.canAddCategory.isChecked()) {
            } else {
                canAddCategory = false;
            }

        });
        binding.canAddItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canAddItem = binding.canAddItem.isChecked()) {
            } else {
                canAddItem = false;
            }
        });

        binding.canDeleteItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canDeleteItem = binding.canDeleteItem.isChecked()) {
            } else {
                canDeleteItem = false;
            }
        });
        binding.canUpdateItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if (canUpdateItem = binding.canUpdateItem.isChecked()) {
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
        if (item.getItemId() == R.id.add_user) {
            binding.showListAllUser.setVisibility(View.GONE);
            binding.layoutAddUser.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}