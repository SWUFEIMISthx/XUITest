package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final ArrayList<LemonTea> lemon_teas_array = new ArrayList<>();
    private final ArrayList<RightListBean> titles_array = new ArrayList<>();
    private RecyclerView right_list_view;
    private RecyclerView left_list_view;
    private LinearLayoutManager right_LLM;
    private TextView right_title;
    private SearchView searchView;
    private AlertDialog choose_dialog = null;
    private AlertDialog.Builder builder = null;
    private View view_choose;
    private Context mContext = this.getActivity();



    public OrderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        SearchView mSearch = (SearchView) view.findViewById(R.id.my_search);
        int id = mSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView text_search = (TextView) mSearch.findViewById(id);
        text_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);


        right_title = (TextView) view.findViewById(R.id.Top_drinkType);
        right_list_view = (RecyclerView) view.findViewById(R.id.rec_right);
        left_list_view = (RecyclerView) view.findViewById(R.id.rec_left);
        searchView = (SearchView) view.findViewById(R.id.my_search);
        builder = new AlertDialog.Builder(this.getActivity());
        builder.setView(view_choose);
        builder.setCancelable(false);
        choose_dialog = builder.create();
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}