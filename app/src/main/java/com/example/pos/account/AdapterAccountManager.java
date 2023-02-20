package com.example.pos.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pos.Database.Entity.UserAccount;
import com.example.pos.R;

import java.util.List;

public class AdapterAccountManager extends BaseAdapter {
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
            view = LayoutInflater.from(context).inflate(R.layout.custom_list_all_user, viewGroup,
                    false);
        }
        long RowCount = getItemId(i)+1;
        String userFirstname, userLastname;
        TextView userRole = view.findViewById(R.id.userRole);
        TextView userName = view.findViewById(R.id.userName);
        TextView userPassword = view.findViewById(R.id.userPassword);
        TextView userNo = view.findViewById(R.id.userId);

        userFirstname = userAccountList.get(i).getFirstname();
        userLastname = userAccountList.get(i).getLastname();
        boolean canDiscount = userAccountList.get(i).getCanDiscount();
        boolean canAddItem = userAccountList.get(i).getCanAddItem();
        boolean canAddCategory = userAccountList.get(i).getCanAddCategory();
        boolean canUpdate = userAccountList.get(i).getCanUpdate();

        boolean isAdmin = userAccountList.get(i).getAdmin();
        boolean isManager = userAccountList.get(i).getManager();
        boolean isCashier = userAccountList.get(i).getCashier();
        boolean isSeller = userAccountList.get(i).getSeller();
        userNo.setText(String.valueOf(RowCount));
        userName.setText(userAccountList.get(i).getUsername());
        userPassword.setText(userAccountList.get(i).getPassword());
        if (isAdmin) {
            userRole.setText("Admin");
        }else if(isManager){
            userRole.setText("Manager");
        }else if(isCashier){
            userRole.setText("Cashier");
        }else if (isSeller){
            userRole.setText("Seller");
        }else {
            userRole.setText("Null");
        }
        return view;
    }
}
