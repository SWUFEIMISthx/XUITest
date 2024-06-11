package com.thx.xuitest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String userName;
    private final ArrayList<Order> orders = new ArrayList<>();
    private RecyclerView order_listview;
    private View view;
    private LinearLayoutManager LLM;
    private  LayoutInflater layoutInflater;

    // TODO: Rename and change types of parameters


    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BillFragment.
     */
    public static BillFragment newInstance(String userName) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        System.out.println("Username in newInstance" + userName);
        args.putString("username", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("username");
            System.out.println("USERNAME" + userName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater = inflater;
        try{
            FileOutputStream fos1;
            FileInputStream fis;
            if(getContext() != null){
                fis = getContext().openFileInput(userName + "bill.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while((s = reader.readLine()) != null){
                    System.out.println(s);
                    s = s.replace("/n", "");
                    orders.add(0, new Order(s));
                }
                reader.close();
                in.close();
            }
        }
        catch (IOException e){
            System.out.println("error in reading");
        }
        view = inflater.inflate(R.layout.fragment_bill, container, false);
        order_listview = (RecyclerView) view.findViewById(R.id.RecView_bill);
        LLM = new LinearLayoutManager(this.getActivity());
        order_listview.setLayoutManager(LLM);
        BillAdapter billAdapter = new BillAdapter(inflater, orders);
        order_listview.setAdapter(billAdapter);
        return view;
    }
    public void refreshing(){
        orders.clear();
        try{
            FileInputStream fis;
            if(getContext() != null){
                fis = getContext().openFileInput(userName + "bill.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null){
                    System.out.println(s);
                    s = s.replace("\n", "");  // 修改 replace("/n", "") 为 replace("\n", "")
                    orders.add(0, new Order(s));
                }
                reader.close();
                in.close();
            }
        }
        catch (IOException e){
            System.out.println("读取错误");
        }
        LLM = new LinearLayoutManager(this.getActivity());
        order_listview.setLayoutManager(LLM);
        BillAdapter billAdapter = new BillAdapter(layoutInflater, orders);
        order_listview.setAdapter(billAdapter);  // 确保设置适配器
    }

    public void onHiddenChanged(boolean hidden){
        super.onHiddenChanged(hidden);
        refreshing();
    }
}