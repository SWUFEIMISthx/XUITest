package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

public class IndexActivity extends AppCompatActivity {
    private RadioGroup rg_tab_bar;
    private RadioButton rb_order;
    private String username;
    private Fragment fg_order;
    private Fragment fg_shop_car;
    private Fragment fg_bill;
    private FragmentManager fragmentManager;
    private static final String TAG = "IndexActivity";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_index);
        username = getIntent().getStringExtra("userName");
        Log.d(TAG, "username in OnCreate in IndexActivity: " + username);

        fragmentManager = getSupportFragmentManager();
        rg_tab_bar = findViewById(R.id.rg_tab);
        rg_tab_bar.setOnCheckedChangeListener(this::onCheckedChanged);

        rb_order = findViewById(R.id.rb_order);
        rb_order.setChecked(true);

        // 显示默认的 Fragment
        defaultFragment();
    }

    @SuppressLint("NonConstantResourceId")
    public void onCheckedChanged(RadioGroup group, int checkedId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragments(fragmentTransaction);

        Log.d(TAG, "Checked ID: " + checkedId);
        if (checkedId == R.id.rb_order) {
            if (fg_order == null) {
                fg_order = new OrderFragment();
                fragmentTransaction.add(R.id.ly_content, fg_order);
            } else {
                fragmentTransaction.show(fg_order);
            }
        } else if (checkedId == R.id.rb_bill) {
            if (fg_bill == null) {
                fg_bill = BillFragment.newInstance(username);
                fragmentTransaction.add(R.id.ly_content, fg_bill);
            } else {
                fragmentTransaction.show(fg_bill);
            }
        } else if (checkedId == R.id.rb_shop_car) {
            if (fg_shop_car == null) {
                fg_shop_car = ShopCarFragment.newInstance(username);
                fragmentTransaction.add(R.id.ly_content, fg_shop_car);
            } else {
                fragmentTransaction.show(fg_shop_car);
            }
        }
        fragmentTransaction.commit();
    }

    public void defaultFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fg_order = new OrderFragment();  // 使用具体的 Fragment 类型
        fragmentTransaction.add(R.id.ly_content, fg_order);
        fragmentTransaction.show(fg_order);
        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction){
        if (fg_order != null) {
            fragmentTransaction.hide(fg_order);
        }
        if (fg_bill != null) {
            fragmentTransaction.hide(fg_bill);
        }
        if (fg_shop_car != null) {
            fragmentTransaction.hide(fg_shop_car);
        }
    }
}
