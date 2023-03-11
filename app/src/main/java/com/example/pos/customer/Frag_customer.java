package com.example.pos.customer;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pos.Configure.DateHelper;
import com.example.pos.Configure.SharedPrefHelper;
import com.example.pos.Database.Entity.Customer;
import com.example.pos.R;
import com.example.pos.databinding.FragmentFragCustomerBinding;
import com.github.drjacky.imagepicker.ImagePicker;

import java.io.File;
import java.util.List;

public class Frag_customer extends Fragment implements doCustomizeCustomer {
    FragmentFragCustomerBinding binding;
    CustomerViewModel viewModel;
    List<Customer> customerList;
    int customerId;
    double customerDiscount;
    String customerName;
    String customerPhone;
    String customerAddress;
    String customerSex;
    String customerProfile;
    Uri uri;
    File file;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCustomerBinding.inflate(getLayoutInflater(), container, false);
        binding.formCustomer.btnSaveCustomer.setOnClickListener(this::SaveCustomer);
        binding.formCustomer.btnDeleteCustomer.setOnClickListener(this::DeleteCustomer);
        binding.formCustomer.btnUpdateCustomer.setOnClickListener(this::UpdateCustomer);
        binding.formCustomer.btnCancelCustomer.setOnClickListener(this::CancelCustomer);
        binding.formCustomer.addCustomerProfile.setOnClickListener(this::OnGetImage);
        onCreateMenu();
        GetAllCustomer();
        CustomerSex();
        return binding.getRoot();
    }

    private void CancelCustomer(View view) {
        OnUpdateUI();
    }

    private void UpdateCustomer(View view) {
        customerName = String.valueOf(binding.formCustomer.customerName.getText());
        customerAddress = String.valueOf(binding.formCustomer.customerAddress.getText());
        customerPhone = String.valueOf(binding.formCustomer.customerPhoneNumber.getText());
        if (String.valueOf(binding.formCustomer.customerName.getText()).isEmpty() ||
                String.valueOf(binding.formCustomer.customerAddress.getText()).isEmpty()
                || String.valueOf(binding.formCustomer.customerPhoneNumber.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Customer, Toast.LENGTH_SHORT).show();
        } else {
            if (String.valueOf(binding.formCustomer.customerDiscount.getText()).isEmpty()) {
                customerDiscount = 0;
                viewModel.updateCustomerById(customerName, customerSex, customerPhone, customerDiscount, customerAddress, customerProfile, customerId);
                OnUpdateUI();
            } else {
                customerDiscount = Double.parseDouble(String.valueOf(binding.formCustomer.customerDiscount.getText()));
                viewModel.updateCustomerById(customerName, customerSex, customerPhone, customerDiscount, customerAddress, customerProfile, customerId);
                OnUpdateUI();
            }


        }


    }

    private void DeleteCustomer(View view) {

        viewModel.deleteCustomerById(customerId);
        OnUpdateUI();
    }

    private void SaveCustomer(View view) {
        customerName = String.valueOf(binding.formCustomer.customerName.getText());
        customerAddress = String.valueOf(binding.formCustomer.customerAddress.getText());
        customerPhone = String.valueOf(binding.formCustomer.customerPhoneNumber.getText());
        if (String.valueOf(binding.formCustomer.customerName.getText()).isEmpty() ||
                String.valueOf(binding.formCustomer.customerAddress.getText()).isEmpty()
                || String.valueOf(binding.formCustomer.customerPhoneNumber.getText()).isEmpty()) {
            Toast.makeText(requireContext(), R.string.Please_Input_Customer, Toast.LENGTH_SHORT).show();
        } else {
            if (String.valueOf(binding.formCustomer.customerDiscount.getText()).isEmpty()) {
                customerDiscount = 0;
                viewModel.createCustomer(new Customer(customerName, customerSex, customerPhone, customerAddress,
                        customerProfile,
                        customerDiscount, SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                        DateHelper.getCurrentDate()));
                OnUpdateUI();
            } else {
                customerDiscount = Double.parseDouble(String.valueOf(binding.formCustomer.customerDiscount.getText()));
                viewModel.createCustomer(new Customer(customerName, customerSex, customerPhone, customerAddress,
                        customerProfile,
                        customerDiscount, SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                        DateHelper.getCurrentDate()));
                OnUpdateUI();
            }
        }
    }


    private void GetAllCustomer() {
        viewModel.getAllCustomer().observe(getViewLifecycleOwner(), customers -> {
            if (customers.size() != 0) {
                customerList = customers;
                binding.txtNoCustomerFound.setVisibility(View.GONE);
                binding.listAllCustomer.setVisibility(View.VISIBLE);
                binding.listAllCustomer.setAdapter(new AdapterCustomer(this, customers, requireContext()));
                binding.listAllCustomer.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(requireContext(), "Loading History.....", Toast.LENGTH_SHORT).show());
//                binding.listAllCustomer.setOnItemClickListener((adapterView, view, i, l) -> {
//                    customerId = customers.get(i).getCustomerId();
//                    customerSex = customers.get(i).getCustomerSex();
//                    customerProfile = customers.get(i).getCustomerProfile();
//                    if (customerSex == null) {
//                        binding.formCustomer.customerMale.setChecked(false);
//                        binding.formCustomer.customerFemale.setChecked(false);
//                    } else if (customerSex.contains("Female")) {
//                        binding.formCustomer.customerFemale.setChecked(true);
//                    } else {
//                        binding.formCustomer.customerMale.setChecked(true);
//                    }
//                    if (customers.get(i).getCustomerProfile() != null) {
//                        Glide.with(this).load(customers.get(i).getCustomerProfile()).into(binding.formCustomer.customerProfile);
//                    } else {
//                        binding.formCustomer.customerProfile.setImageResource(R.drawable.admin_profile);
//                    }
//                    binding.formCustomer.customerDiscount.setText(String.valueOf(customers.get(i).getCustomerDiscount()));
//                    binding.formCustomer.customerName.setText(customers.get(i).getCustomerName());
//                    binding.formCustomer.customerPhoneNumber.setText(customers.get(i).getCustomerPhoneNumber());
//                    binding.formCustomer.customerAddress.setText(customers.get(i).getCustomerAddress());
//                    ShowAddCustomer();
//                    DeleteUpdate();
//                });
            }
        });//viewModel Get All Customer
    }

    private void CustomerSex() {
        binding.formCustomer.customSex.setOnCheckedChangeListener((radioGroup, i) -> {
            if (binding.formCustomer.customerMale.isChecked()) {
                customerSex = "Male";
            } else {
                customerSex = "Female";
            }
        });
    }//customer sex

    private void OnGetImage(View view) {
        launcher.launch(ImagePicker.Companion.with(requireActivity()).maxResultSize(1080, 1080, true).crop().galleryOnly().createIntent()

        );

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            uri = result.getData().getData();
            file = new File(uri.getPath());
            customerProfile = file.toString();
            // Use the uri to load the image
            binding.formCustomer.customerProfile.setImageURI(uri);
        } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), "No Image Pick", Toast.LENGTH_SHORT).show();
            // Use ImagePicker.Companion.getError(result.getData()) to show an error
        }
    });


    @Override
    public void onDestroyView() {
        viewModel.getAllCustomer().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();

    }

    private void onCreateMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu);
                menu.findItem(R.id.add_customer).setVisible(true);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.add_customer) {
                    binding.txtNoCustomerFound.setVisibility(View.GONE);
                    ShowAddCustomer();
                }
                return true;
            }
        }, getViewLifecycleOwner());
    }

    private void ShowAddCustomer() {
        binding.listAllCustomer.setVisibility(View.GONE);
        binding.formCustomer.layoutAddCustomer.setVisibility(View.VISIBLE);
    }

    private void DeleteUpdate() {
        binding.formCustomer.btnUpdateCustomer.setVisibility(View.VISIBLE);
        binding.formCustomer.btnDeleteCustomer.setVisibility(View.VISIBLE);
        binding.formCustomer.btnSaveCustomer.setVisibility(View.GONE);
    }

    private void OnUpdateUI() {
        binding.formCustomer.customerProfile.setImageResource(R.drawable.admin_profile);
        binding.formCustomer.customerName.setText(null);
        binding.formCustomer.customerPhoneNumber.setText(null);
        binding.formCustomer.customerAddress.setText(null);
        binding.formCustomer.customerDiscount.setText(null);
        binding.formCustomer.btnUpdateCustomer.setVisibility(View.GONE);
        binding.formCustomer.btnDeleteCustomer.setVisibility(View.GONE);
        binding.formCustomer.btnSaveCustomer.setVisibility(View.VISIBLE);
        binding.formCustomer.layoutAddCustomer.setVisibility(View.GONE);
        binding.listAllCustomer.setVisibility(View.VISIBLE);
    }

    @Override
    public void doCustomizeCustomerById(int i) {
        customerId = i;
        for (Customer c : customerList) {
            if (c.getCustomerId() == customerId) {
                binding.formCustomer.layoutAddCustomer.setVisibility(View.VISIBLE);
                binding.listAllCustomer.setVisibility(View.GONE);
                DeleteUpdate();
                if (c.getCustomerProfile() != null) {
                    Glide.with(this).load(c.getCustomerProfile()).into(binding.formCustomer.customerProfile);
                } else {
                    binding.formCustomer.customerProfile.setImageResource(R.drawable.admin_profile);
                }
                if (c.getCustomerSex() == null) {
                    binding.formCustomer.customerMale.setChecked(false);
                    binding.formCustomer.customerFemale.setChecked(false);
                } else if (c.getCustomerSex().contains("Male")) {
                    binding.formCustomer.customerMale.setChecked(true);
                } else if (c.getCustomerSex().contains("Female")) {
                    binding.formCustomer.customerFemale.setChecked(true);
                }

                binding.formCustomer.customerName.setText(c.getCustomerName());
                binding.formCustomer.customerAddress.setText(c.getCustomerAddress());
                binding.formCustomer.customerDiscount.setText(String.valueOf(c.getCustomerDiscount()));
                binding.formCustomer.customerPhoneNumber.setText(c.getCustomerPhoneNumber());
            }
        }
    }
}