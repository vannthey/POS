package com.example.pos.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.R;
import com.example.pos.databinding.CustomListAllUserBinding;

import java.util.List;

public class AdapterAccountManager extends BaseAdapter {
    CustomListAllUserBinding binding;
    List<UserAccount> userAccountList;
    Context context;

    public AdapterAccountManager(List<UserAccount> userAccountList, Context context) {
        this.userAccountList = userAccountList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userAccountList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            binding = CustomListAllUserBinding.inflate(LayoutInflater.from(context), viewGroup,
                    false);
            view = binding.getRoot();
        }
        binding.userName.setText(userAccountList.get(i).getUsername());
        binding.userRole.setText(userAccountList.get(i).getUserRole());
        return view;
    }
}
