package com.thx.xuitest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BillFragment extends Fragment {

    private String userName;
    private final ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Order> filteredOrders = new ArrayList<>();
    private RecyclerView order_listview;
    private View view;
    private LinearLayoutManager LLM;
    private LayoutInflater layoutInflater;
    private BillAdapter billAdapter;

    public BillFragment() {
        // Required empty public constructor
    }

    public static BillFragment newInstance(String userName) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("username", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("username");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutInflater = inflater;
        view = inflater.inflate(R.layout.fragment_bill, container, false);

        loadOrders();

        order_listview = view.findViewById(R.id.RecView_bill);
        LLM = new LinearLayoutManager(this.getActivity());
        order_listview.setLayoutManager(LLM);

        billAdapter = new BillAdapter(inflater, filteredOrders);
        order_listview.setAdapter(billAdapter);

        setupSearchView();

        return view;
    }

    private void loadOrders() {
        orders.clear();
        try {
            FileInputStream fis;
            if (getContext() != null) {
                fis = getContext().openFileInput(userName + "bill.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null) {
                    orders.add(0, new Order(s));
                }
                reader.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        filteredOrders.addAll(orders);
    }

    private void setupSearchView() {
        SearchView searchView = view.findViewById(R.id.search_view);

        searchView.setQueryHint("输入订单编号或日期查询");

        // 更改搜索图标颜色
        int searchIconId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView searchIcon = searchView.findViewById(searchIconId);
        if (searchIcon != null) {
            searchIcon.setColorFilter(getResources().getColor(R.color.gray_btn), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        // 更改清除图标颜色
        int closeIconId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeIcon = searchView.findViewById(closeIconId);
        if (closeIcon != null) {
            closeIcon.setColorFilter(getResources().getColor(R.color.gray_btn), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        // 更改搜索文字颜色
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setHintTextColor(getResources().getColor(R.color.gray_btn));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterOrders(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterOrders(newText);
                return true;
            }
        });
    }

    private void filterOrders(String query) {
        filteredOrders.clear();
        for (Order order : orders) {
            if (order.getOrder_number().contains(query) || order.getTime().contains(query)) {
                filteredOrders.add(order);
            }
        }
        billAdapter.notifyDataSetChanged();
    }

    public void refreshing() {
        loadOrders();
        filterOrders(""); // Reset filter to show all orders
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshing();
        }
    }
}
