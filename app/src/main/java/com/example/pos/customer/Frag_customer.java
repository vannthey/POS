package com.example.pos.customer;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pos.Database.Entity.Customer;
import com.example.pos.DateHelper;
import com.example.pos.R;
import com.example.pos.SharedPrefHelper;
import com.example.pos.databinding.FragmentFragCustomerBinding;

public class Frag_customer extends Fragment {
    FragmentFragCustomerBinding binding;
    CustomerViewModel viewModel;
    Handler handler;
    int customerId;

    double customerDiscount;
    String customerName;
    String customerPhone;
    String customerAddress;
    String customerSex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handler = new Handler();
        viewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragCustomerBinding.inflate(getLayoutInflater(), container, false);
        binding.btnSaveCustomer.setOnClickListener(this::SaveCustomer);
        binding.btnDeleteCustomer.setOnClickListener(this::DeleteCustomer);
        binding.btnUpdateCustomer.setOnClickListener(this::UpdateCustomer);
        binding.btnCancelCustomer.setOnClickListener(this::CancelCustomer);
        onCreateMenu();
        GetAllCustomer();
        CustomerSex();
        return binding.getRoot();
    }

    private void CancelCustomer(View view) {
        OnUpdateUI();
    }

    private void UpdateCustomer(View view) {
        customerName = String.valueOf(binding.customerName.getText());
        customerAddress = String.valueOf(binding.customerAddress.getText());
        customerPhone = String.valueOf(binding.customerPhoneNumber.getText());
        if (String.valueOf(binding.customerName.getText()).isEmpty() ||
                String.valueOf(binding.customerAddress.getText()).isEmpty()
                || String.valueOf(binding.customerPhoneNumber.getText()).isEmpty()) {
            if (String.valueOf(binding.customerDiscount.getText()).isEmpty()) {
                customerDiscount = 0;
            } else {
                customerDiscount = Double.parseDouble(String.valueOf(binding.customerDiscount.getText()));
            }
            Toast.makeText(requireContext(), R.string.Please_Input_Customer, Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                if (customerName != null) {
                    viewModel.updateCustomerById(customerName, customerSex, customerPhone, customerDiscount, customerAddress, customerId);
                    handler.post(this::OnUpdateUI);
                }
            }).start();
        }


    }

    private void DeleteCustomer(View view) {
        new Thread(() -> {
            viewModel.deleteCustomerById(customerId);
            handler.post(this::OnUpdateUI);
        }).start();
    }

    private void SaveCustomer(View view) {
        customerName = String.valueOf(binding.customerName.getText());
        customerAddress = String.valueOf(binding.customerAddress.getText());
        customerPhone = String.valueOf(binding.customerPhoneNumber.getText());
        if (String.valueOf(binding.customerName.getText()).isEmpty() ||
                String.valueOf(binding.customerAddress.getText()).isEmpty()
                || String.valueOf(binding.customerPhoneNumber.getText()).isEmpty()) {
            if (String.valueOf(binding.customerDiscount.getText()).isEmpty()) {
                customerDiscount = 0;
            } else {
                customerDiscount = Double.parseDouble(String.valueOf(binding.customerDiscount.getText()));
            }
            Toast.makeText(requireContext(), R.string.Please_Input_Customer, Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                if (customerName != null) {
                    viewModel.createCustomer(new Customer(customerName, customerSex, customerPhone, customerAddress,
                            customerDiscount, SharedPrefHelper.getInstance().getSaveUserLoginName(requireContext()),
                            DateHelper.getCurrentDate()));
                    handler.post(this::OnUpdateUI);
                }
            }).start();
        }
    }


    private void GetAllCustomer() {
        viewModel.getAllCustomer().observe(getViewLifecycleOwner(), customers -> {
            if (customers.size() != 0) {
                binding.txtNoCustomerFound.setVisibility(View.GONE);
                binding.listAllCustomer.setVisibility(View.VISIBLE);
                binding.listAllCustomer.setAdapter(new AdapterCustomer(customers, requireContext()));
                binding.listAllCustomer.setOnItemClickListener((adapterView, view, i, l) -> {
                    customerId = customers.get(i).getCustomerId();
                    customerSex = customers.get(i).getCustomerSex();
                    if (customerSex == null) {
                        binding.customerMale.setChecked(false);
                        binding.customerFemale.setChecked(false);
                    } else if (customerSex.contains("Female")) {
                        binding.customerFemale.setChecked(true);
                    } else {
                        binding.customerMale.setChecked(true);
                    }
                    binding.customerDiscount.setText(String.valueOf(customers.get(i).getCustomerDiscount()));
                    binding.customerName.setText(customers.get(i).getCustomerName());
                    binding.customerPhoneNumber.setText(customers.get(i).getCustomerPhoneNumber());
                    binding.customerAddress.setText(customers.get(i).getCustomerAddress());

                    ShowAddCustomer();
                    DeleteUpdate();
                });
            }
        });
    }

    private void CustomerSex() {
        binding.customSex.setOnCheckedChangeListener((radioGroup, i) -> {
            if (binding.customerMale.isChecked()) {
                customerSex = "Male";
            } else {
                customerSex = "Female";
            }
        });
    }


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
        binding.layoutAddCustomer.setVisibility(View.VISIBLE);
    }

    private void DeleteUpdate() {
        binding.btnUpdateCustomer.setVisibility(View.VISIBLE);
        binding.btnDeleteCustomer.setVisibility(View.VISIBLE);
        binding.btnSaveCustomer.setVisibility(View.GONE);
    }

    private void OnUpdateUI() {
        binding.customerName.setText(null);
        binding.customerPhoneNumber.setText(null);
        binding.customerAddress.setText(null);
        binding.customerDiscount.setText(null);
        binding.btnUpdateCustomer.setVisibility(View.GONE);
        binding.btnDeleteCustomer.setVisibility(View.GONE);
        binding.btnSaveCustomer.setVisibility(View.VISIBLE);
        binding.layoutAddCustomer.setVisibility(View.GONE);
        binding.listAllCustomer.setVisibility(View.VISIBLE);
    }

}