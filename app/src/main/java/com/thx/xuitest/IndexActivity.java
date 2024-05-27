package com.thx.xuitest;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class IndexActivity extends AppCompatActivity {
    private RadioGroup rg_tab_bar;
    private RadioButton rb_order;
    private String username;
    private Fragment fg_order;
    private Fragment fg_shop_car;
    private Fragment fg_bill;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_index);
        username = getIntent().getStringExtra("username");
        System.out.println("username in OnCreate in IndexActivity" + username);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab);
//        rg_tab_bar.setOnCheckedChangeListener(this::OncheckedChanged);
    }
    public void onCheckedChanged(RadioGroup gruop, int checkedId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fg_order = new OrderFragment();
    }
}
