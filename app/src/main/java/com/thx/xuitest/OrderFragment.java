package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;


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


    @SuppressLint({"CutPasteId", "InflateParams"})
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
        view_choose = inflater.inflate(R.layout.order_details, null, false);
        builder.setCancelable(false);
        choose_dialog = builder.create();
        view_choose.findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = "中杯";
                String tempearture = "全冰";
                String sugar = "全糖";
                RadioGroup radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_size);
                for(int i=0;i<radiogroup.getChildCount();i++){
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if(rd.isChecked()){
                        size = String.valueOf(rd.getText());
                    }
                }
                radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_ice);
                for(int i=0;i<radiogroup.getChildCount();i++){
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if(rd.isChecked()){
                        tempearture = String.valueOf(rd.getText());
                    }
                }
                radiogroup = (RadioGroup) view_choose.findViewById(R.id.radioGroup_sugar);
                for(int i=0;i<radiogroup.getChildCount();i++){
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if(rd.isChecked()){
                        sugar = String.valueOf(rd.getText());
                    }
                }

                //加入购物车的实现

                TextView drinkName = view_choose.findViewById(R.id.choose_drinkName);
                System.out.println("drinkName:" + String.valueOf(drinkName.getText()).split(" #")[0]);
                Drinks drink = new Drinks(Integer.parseInt(String.valueOf(drinkName.getText()).split(" #")[1]));
                Flavors flavor = new Flavors(size, tempearture, sugar);
                TextView num_textview = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int number = Integer.parseInt((String) num_textview.getText());
                OrderedDrinks od = new OrderedDrinks(drink, flavor, number);
                choose_dialog.dismiss();
            }
        });
        view_choose.findViewById(R.id.button_subtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if(i > 1){
                    i--;
                }
                numberText.setText(String.valueOf(i));
            }
        });
        view_choose.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = (TextView) view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if(i < 100){
                    i++;
                }
                numberText.setText(String.valueOf(i));
            }
        });

        initData();
        right_LLM = new LinearLayoutManager(this.getActivity());
        right_list_view.setLayoutManager(right_LLM);
        RightAdapter rightAdapter = new RightAdapter(inflater, lemon_teas_array);
        right_list_view.setAdapter(rightAdapter);

        titles_array.get(0).setSelect(true);
        left_list_view.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        LeftAdapter leftAdapter = new LeftAdapter(titles_array);
        left_list_view.setAdapter(leftAdapter);


        right_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemPosition = right_LLM.findFirstVisibleItemPosition();
                leftAdapter.setCurrentPosition(firstItemPosition);
                if(!Objects.equals(leftAdapter.getCurrentTitle(), "")){
                    right_title.setText(leftAdapter.getCurrentTitle());
                }
            }
        });
        leftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int rightPosition) {
                if(right_LLM != null){
                    right_LLM.scrollToPositionWithOffset(rightPosition,0);
                }
            }
        });
        rightAdapter.buttonSetOnClick(new RightAdapter.MyClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onclick(View v, int position) {
                choose_dialog.show();
                if(view_choose != null){
                    LemonTea lemontea = lemon_teas_array.get(position);
                    ImageView img =view_choose.findViewById(R.id.choose_drink_img);
                    img.setImageResource(lemontea.getImageResId() - 1);
                    TextView name = view_choose.findViewById(R.id.choose_drinkName);
                    name.setText(lemontea.get_name() + " #" + (lemontea.get_number() + 1));
                    TextView intro = view_choose.findViewById(R.id.choose_drinkIntro);
                    intro.setText(lemontea.get_introduction());
                    TextView drink_number = view_choose.findViewById(R.id.textView_drinkNumber);
                    drink_number.setText("1");
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                for(int i=0;i<lemon_teas_array.size();i++){
                    if(lemon_teas_array.get(i).get_name().contains(queryText)){
                        if(right_LLM != null){
                            right_LLM.scrollToPositionWithOffset(i, 0);
                            break;
                        }
                    }
                }
                return true;
            }
        });
        return view;
    }
    private void initData(){

    }
}