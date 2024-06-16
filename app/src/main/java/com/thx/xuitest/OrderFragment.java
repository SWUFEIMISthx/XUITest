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

import com.bumptech.glide.Glide;
import com.xuexiang.xui.widget.textview.MarqueeTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrderFragment extends Fragment {
    private final ArrayList<Drinks> lemon_teas_array = new ArrayList<>();
    private final ArrayList<RightListBean> titles_array = new ArrayList<>();
    private RecyclerView right_list_view;
    private RecyclerView left_list_view;
    private LinearLayoutManager right_LLM;
    private TextView right_title;
    private SearchView searchView;
    private AlertDialog choose_dialog = null;
    private AlertDialog.Builder builder = null;
    private View view_choose;
    private Context mContext;

    private MarqueeTextView marqueeTextView;

    public OrderFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"CutPasteId", "InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        mContext = this.getActivity();

        marqueeTextView = view.findViewById(R.id.marqueeTextView);
        setupMarqueeText();

        SearchView mSearch = view.findViewById(R.id.my_search);
        int id = mSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView text_search = mSearch.findViewById(id);
        text_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        right_title = view.findViewById(R.id.Top_drinkType);
        right_list_view = view.findViewById(R.id.rec_right);
        left_list_view = view.findViewById(R.id.rec_left);
        searchView = view.findViewById(R.id.my_search);

        builder = new AlertDialog.Builder(this.getActivity());
        view_choose = inflater.inflate(R.layout.order_details, null, false);
        builder.setView(view_choose);
        builder.setCancelable(false);
        choose_dialog = builder.create();

        view_choose.findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = "中杯";
                String tempearture = "全冰";
                String sugar = "全糖";
                RadioGroup radiogroup = view_choose.findViewById(R.id.radioGroup_size);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        size = String.valueOf(rd.getText());
                    }
                }
                radiogroup = view_choose.findViewById(R.id.radioGroup_ice);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        tempearture = String.valueOf(rd.getText());
                    }
                }
                radiogroup = view_choose.findViewById(R.id.radioGroup_sugar);
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radiogroup.getChildAt(i);
                    if (rd.isChecked()) {
                        sugar = String.valueOf(rd.getText());
                    }
                }

                //加入购物车的实现

                TextView drinkName = view_choose.findViewById(R.id.choose_drinkName);
                Drinks drink = findDrinkByName(String.valueOf(drinkName.getText()));
                Flavors flavor = new Flavors(size, tempearture, sugar);
                TextView num_textview = view_choose.findViewById(R.id.textView_drinkNumber);
                int number = Integer.parseInt((String) num_textview.getText());
                OrderedDrinks od = new OrderedDrinks(drink, flavor, number);
                choose_dialog.dismiss();
            }
        });

        view_choose.findViewById(R.id.button_subtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if (i > 1) {
                    i--;
                }
                numberText.setText(String.valueOf(i));
            }
        });

        view_choose.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = view_choose.findViewById(R.id.textView_drinkNumber);
                int i = Integer.parseInt(String.valueOf(numberText.getText()));
                if (i < 100) {
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
                if (!Objects.equals(leftAdapter.getCurrentTitle(), "")) {
                    right_title.setText(leftAdapter.getCurrentTitle());
                }
            }
        });

        leftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int rightPosition) {
                if (right_LLM != null) {
                    right_LLM.scrollToPositionWithOffset(rightPosition, 0);
                }
            }
        });

        rightAdapter.buttonSetOnClick(new RightAdapter.MyClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onclick(View v, int position) {
                choose_dialog.show();
                if (view_choose != null) {
                    Drinks lemontea = lemon_teas_array.get(position);
                    ImageView img = view_choose.findViewById(R.id.choose_drink_img);
                    Glide.with(mContext).load(lemontea.getImageUrl()).into(img);
                    TextView name = view_choose.findViewById(R.id.choose_drinkName);
                    name.setText(lemontea.get_name());  // 移除序号
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
                for (int i = 0; i < lemon_teas_array.size(); i++) {
                    if (lemon_teas_array.get(i).get_name().contains(queryText)) {
                        if (right_LLM != null) {
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

    private void setupMarqueeText() {
        List<String> marqueeTexts = Arrays.asList(
                "欢迎光临 Lemon Bliss! 特别推荐：手爆前男友柠檬茶，酸甜解渴，直击灵魂！",
                "试试我们的特色茶：和气桃桃，新鲜柠檬和乌龙茶的完美结合。"
        );
        marqueeTextView.setSpeed(2)
                .setDisplaySimpleList(marqueeTexts).startRoll()
                .setScrollWidth(marqueeTextView.getWidth())
                .startRoll();
    }

    private void initData() {
        lemon_teas_array.add(new Drinks("原谅绿柠檬茶", "\uD83D\uDC4D 招牌推荐",
                14f, "精选绿茶与柠檬，产生美妙味道，展现泰式风味", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%8B%9B%E7%89%8C%E6%8E%A8%E8%8D%90_%E5%8E%9F%E8%B0%85%E7%BB%BF.jpg"));
        lemon_teas_array.add(new Drinks("鸭屎香柠檬茶", 14f, "酸甜解渴，清爽清新，浓郁的茶香", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%8B%9B%E7%89%8C%E6%8E%A8%E8%8D%90_%E9%B8%AD%E5%B1%8E%E9%A6%99.jpg"));
        lemon_teas_array.add(new Drinks("手爆前男友柠檬茶", 14f, "酸甜解渴，直击灵魂", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%8B%9B%E7%89%8C%E6%8E%A8%E8%8D%90_%E6%89%8B%E7%88%86%E5%89%8D%E7%94%B7%E5%8F%8B.jpg"));
        lemon_teas_array.add(new Drinks("手爆绿茶妹柠檬茶", 12f, "绿茶作为基底，满口清香", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%8B%9B%E7%89%8C%E6%8E%A8%E8%8D%90_%E7%BB%BF%E8%8C%B6%E5%A6%B9.jpg"));

        lemon_teas_array.add(new Drinks("大菠妹（菠萝）柠檬茶", "\uD83C\uDF53 果茶推荐",
                14f, "新鲜柠檬与菠萝，茉莉花茶", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%9E%9C%E8%8C%B6%E6%8E%A8%E8%8D%90_%E5%A4%A7%E8%8F%A0%E5%A6%B9.jpg"));
        lemon_teas_array.add(new Drinks("粉红少女（芭乐）柠檬茶", 14f, "红芭乐，新鲜柠檬", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E6%9E%9C%E8%8C%B6%E6%8E%A8%E8%8D%90_%E7%B2%89%E7%BA%A2.jpg"));

        lemon_teas_array.add(new Drinks("和气桃桃", "\uD83C\uDFDD 特色推荐",
                12f, "乌龙茶，新鲜柠檬", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E7%89%B9%E8%89%B2%E6%8E%A8%E8%8D%90_%E5%92%8C%E6%B0%94%E6%A1%83%E6%A1%83.jpg"));
        lemon_teas_array.add(new Drinks("白色恋人", 14f, "椰乳遇到酸性的柠檬容易分层，属于正常现象", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E7%89%B9%E8%89%B2%E6%8E%A8%E8%8D%90_%E7%99%BD%E8%89%B2%E6%81%8B%E4%BA%BA.jpg"));

        lemon_teas_array.add(new Drinks("茉莉香鲜奶茶", "\uD83E\uDDCB 奶茶推荐",
                16f, "纯牛奶，茉莉花茶", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E5%A5%B6%E8%8C%B6%E6%8E%A8%E8%8D%90_%E8%8C%89%E8%8E%89%E9%A6%99.jpg"));

        lemon_teas_array.add(new Drinks("茶冻", "\uD83E\uDDE1 加点小料",
                1f, "搭配小料更好喝哦", "https://images-special.oss-cn-chengdu.aliyuncs.com/Android_Images/%E5%8A%A0%E7%82%B9%E5%B0%8F%E6%96%99_%E8%8C%B6%E5%86%BB.jpg"));
        for(int i = 0;i < lemon_teas_array.size(); i++){
            Drinks temp = lemon_teas_array.get(i);
            if(temp.get_type() != null){
                titles_array.add(new RightListBean(i, temp.get_type()));
            }
        }
    }

    private Drinks findDrinkByName(String name) {
        for (Drinks drink : lemon_teas_array) {
            if (drink.get_name().equals(name)) {
                return drink;
            }
        }
        return null;
    }
}
