package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopCarFragment extends Fragment {

    private RecyclerView shopping_list;
    private LinearLayoutManager LLM;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView textView_cost;
    private EditText editText_people;
    private EditText editText_table;
    private CheckBox checkBox_takeaway;
    private ImageButton btn_del;
    private Button btn_buy;
    private int drinkCost;
    private float serviceCost;
    private AlertDialog buyDialog = null;
    private AlertDialog.Builder builder = null;
    private View view_bought;
    private View view_default;
    private String username;

    public ShopCarFragment() {
        // Required empty public constructor
    }

    public static ShopCarFragment newInstance(String username) {
        ShopCarFragment fragment = new ShopCarFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
    }

    @SuppressLint({"DefaultLocale", "InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        layoutInflater = inflater;
        view_default = inflater.inflate(R.layout.fragment_shop_car, container, false);
        shopping_list = view_default.findViewById(R.id.RV_bill);
        LLM = new LinearLayoutManager(this.getActivity());
        shopping_list.setLayoutManager(LLM);

        OrderAdapter orderAdapter = new OrderAdapter(inflater, OrderedDrinks.getOrdered_array());
        orderAdapter.setOnClickListener(new OrderAdapter.MyClickListener() {
            @Override
            public void onAddClick(View v, int position) {
                OrderedDrinks.addDrink(position);
                refreshing();
            }

            @Override
            public void onSubClick(View v, int position) {
                OrderedDrinks.subtractDrink(position);
                refreshing();
            }
        });

        shopping_list.setAdapter(orderAdapter);
        textView_cost = view_default.findViewById(R.id.textView_cost);
        editText_people = view_default.findViewById(R.id.editText_people);
        editText_table = view_default.findViewById(R.id.editText_table);
        checkBox_takeaway = view_default.findViewById(R.id.checkBox);
        btn_del = view_default.findViewById(R.id.button_delete);
        btn_buy = view_default.findViewById(R.id.button_buy);
        drinkCost = OrderedDrinks.getDrinkCost();
        serviceCost = 0.1f;

        if (!editText_people.getText().toString().equals("")) {
            serviceCost = 0.1f * Integer.parseInt(editText_people.getText().toString());
        }

        textView_cost.setText(String.format("价格：￥ %d \n餐位费：￥ %.1f", drinkCost, serviceCost));
        editText_people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    s.append("1");
                }
                if (s.toString().equals("0")) {
                    s.replace(0, 1, "1");
                }
                serviceCost = 0.1f;
                if (!editText_people.getText().toString().equals("")) {
                    serviceCost = 0.1f * Integer.parseInt(editText_people.getText().toString());
                }
                textView_cost.setText(String.format("价格：￥ %d \n餐位费：￥ %.1f", drinkCost, serviceCost));
            }
        });
        editText_table.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    s.append("1");
                }
                if (Integer.valueOf(s.toString()) <= 0) {
                    s.replace(0, 1, "1");
                }
                if (Integer.valueOf(s.toString()) > 30) {
                    s.replace(0, s.length(), "30");
                }
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "购物车已清空！", Toast.LENGTH_SHORT).show();
                OrderedDrinks.clearOrdered_array();
                refreshing();
            }
        });
        builder = new AlertDialog.Builder(this.getActivity());
        view_bought = inflater.inflate(R.layout.box_bought, null, false);
        builder.setView(view_bought);
        builder.setCancelable(false);
        buyDialog = builder.create();
        view_bought.findViewById(R.id.button_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDialog.dismiss();
            }
        });
        view_bought.findViewById(R.id.button_bought).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account temp = new Account(username, context);
                String takeAway = "0";
                if (checkBox_takeaway.isChecked()) {
                    takeAway = "1";
                }
                String cost = String.format("%.1f", drinkCost + serviceCost);
                temp.saveBill(takeAway, cost);
                OrderedDrinks.clearOrdered_array();
                refreshing();
                buyDialog.dismiss();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drinkCost == 0) {
                    Toast.makeText(getContext(), "您还没有选购任何商品", Toast.LENGTH_SHORT).show();
                } else {
                    buyDialog.show();
                    if (view_bought != null) {
                        TextView all_cost = view_bought.findViewById(R.id.textView_allCost);
                        all_cost.setText(String.format("价格：￥ %d\n餐位费：￥ %.1f\n总价：￥ %.1f\n请扫描以下二维码进行支付。", drinkCost, serviceCost, drinkCost + serviceCost));
                    }
                }
            }
        });
        return view_default;
    }

    @SuppressLint("DefaultLocale")
    private void refreshing() {
        ArrayList<OrderedDrinks> orderedList = OrderedDrinks.getOrdered_array();
        Log.d("ShopCarFragment", "Ordered Drinks size: " + orderedList.size());
        for (OrderedDrinks drink : orderedList) {
            Log.d("ShopCarFragment", "Ordered Drink: " + drink.get_drink().get_name() + ", Number: " + drink.get_drink_number());
        }

        OrderAdapter orderAdapter = new OrderAdapter(layoutInflater, orderedList);
        orderAdapter.setOnClickListener(new OrderAdapter.MyClickListener() {
            @Override
            public void onAddClick(View v, int position) {
                OrderedDrinks.addDrink(position);
                refreshing();
            }

            @Override
            public void onSubClick(View v, int position) {
                OrderedDrinks.subtractDrink(position);
                refreshing();
            }
        });

        shopping_list.setAdapter(orderAdapter);
        drinkCost = OrderedDrinks.getDrinkCost();
        serviceCost = 0.2f;
        if (!editText_people.getText().toString().equals("")) {
            serviceCost = 0.2f * Integer.parseInt(editText_people.getText().toString());
        }
        textView_cost.setText(String.format("价格：￥ %d \n餐位费：￥ %.1f", drinkCost, serviceCost));
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refreshing();
    }
}
