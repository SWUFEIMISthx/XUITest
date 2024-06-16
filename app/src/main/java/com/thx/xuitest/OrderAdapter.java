package com.thx.xuitest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private final ArrayList<OrderedDrinks> list;
    private final LayoutInflater inflater;
    private MyClickListener listener;

    public OrderAdapter(LayoutInflater inflater, ArrayList<OrderedDrinks> list) {
        this.list = list;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.bill_item, viewGroup, false);
        return new OrderViewHolder(view);
    }

    private OrderedDrinks getItem(int position) {
        return list.get(position);
    }

    public void setOnClickListener(MyClickListener listener) {
        this.listener = listener;
    }

    public interface MyClickListener {
        void onAddClick(View v, int position);

        void onSubClick(View v, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderedDrinks target = getItem(position);
        holder.bindBean(target);
        holder.btn_add.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddClick(v, holder.getAbsoluteAdapterPosition());
            }
        });
        holder.btn_sub.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSubClick(v, holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView drink_name;
        private final TextView drink_intro;
        private final TextView drink_price;
        private final TextView drink_num;
        private ImageButton btn_add;
        private ImageButton btn_sub;
        private final ImageView drink_image;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            drink_name = itemView.findViewById(R.id.Text_drinkName);
            drink_intro = itemView.findViewById(R.id.Text_drinkIntro);
            drink_price = itemView.findViewById(R.id.Text_drinkPrice);
            drink_image = itemView.findViewById(R.id.img_drink);
            drink_num = itemView.findViewById(R.id.textView_drinkNumber);
            btn_add = itemView.findViewById(R.id.button_add);
            btn_sub = itemView.findViewById(R.id.button_subtract);
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        void bindBean(final OrderedDrinks bean) {
            drink_name.setText(bean.get_drink().get_name() + " #" + (bean.get_drink().get_number() + 1));

            // 使用 Glide 加载图片
            Glide.with(drink_image.getContext())
                    .load(bean.get_drink().getImageUrl())
                    .into(drink_image);

            if (bean.get_flavor().getSize().equals("中杯")) {
                drink_price.setText(String.format("￥ %.0f", (bean.get_drink().get_price()) * bean.get_drink_number()));
            } else if (bean.get_flavor().getSize().equals("小杯")) {
                drink_price.setText(String.format("￥ %.0f", (bean.get_drink().get_price() - 2) * bean.get_drink_number()));
            } else {
                drink_price.setText(String.format("￥ %.0f", (bean.get_drink().get_price() + 2) * bean.get_drink_number()));
            }
            drink_intro.setText(bean.get_flavor().toString());
            drink_num.setText(String.valueOf(bean.get_drink_number()));
        }
    }
}
